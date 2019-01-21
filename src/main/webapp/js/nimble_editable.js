;

(function ($) {
    function unSelection() {
        if (window.getSelection) {
            if (window.getSelection().empty) {  // Chrome
                window.getSelection().empty();
            } else if (window.getSelection().removeAllRanges) {  // Firefox
                window.getSelection().removeAllRanges();
            }
        } else if (document.selection) {  // IE?
            document.selection.empty();
        }
    }

    /**
     *    这个函数把一个单元格变成可编辑的，并且在编辑结束后，调用回调
     */
    $.fn.editTdWithInput = function (callback) {
        var m = $(this);

        // 保存旧参数
        var o = m.attr("style");
        m.attr("oldStyle", o);

        m.css("padding", "0pt");

        var width = m.width();
        var height = m.height();
        m.width(width);

        var input = $("<input>");
        input.width(width);
        input.height(height);

        var oldValue = m.html();
        input.val(oldValue);

        m.html(input);

        var handle = function () {
            var value = input.val();
            m.html(value);

            var o = m.attr("oldStyle");
            if (o)
                m.attr("style", o);
            else
                m.removeAttr("style");
            if (callback && oldValue != value) {
                if (!oldValue && !value) return;
                callback(value);
            }
        };

        input.blur(handle);

        input.bind('keyup', function (event) {
            if (event.keyCode == "13") {
                event.preventDefault();
                handle();
            }
        });

        input.select();
    };

    /**
     *    这个函数把一个单元格变成选择框，并且在编辑结束后，调用回调
     */
    function editTdWithSelect(th, td, ref, callback) {
        unSelection();
        var m = $(td);

        var oldValue = m.attr("val");
        var input = $("<select>");

        // 保存旧参数
        var o = m.attr("style");
        m.attr("oldStyle", o);

        m.css("padding", "0pt");

        var width = m.width();
        var height = m.height();
        m.width(width);

        input.width(width);
        input.height(height);
        // input.css("border-radius", 2);

        input.blur(function () {
            var value = input.val();
            m.attr("val", value);
            m.html(value ? ref[value] : "");

            var o = m.attr("oldStyle");
            if (o)
                m.attr("style", o);
            else
                m.removeAttr("style");

            if (callback && oldValue != value) {
                if (!oldValue && !value) return;
                callback(value);
            }
        });

        if (th.attr("empty")) {
            var option = $("<option>");
            option.val("");
            input.append(option);
        }

        $.each(ref, function (key, value) {
            var option = $("<option>");
            if (key == oldValue)
                option.attr("selected", "selected");
            option.val(key);
            option.html(value);
            input.append(option);
        });

        m.html(input);
        input.select();

        // 打开组合框
        var event;
        event = document.createEvent('MouseEvents');
        event.initMouseEvent('mousedown', true, true, window);
        input[0].dispatchEvent(event);
    }

    function getUrl(table) {
        var url = table.attr("url");
        if (!url) url = table.attr("name");
        if (!url) url = table.attr("id");
        return url;
    }

    function getKeyField(table) {
        var keyField = table.attr("keyField");   // 表的主键字段
        return keyField ? keyField : "id";
    }

    /**
     * 返回一个用来保存数据的函数
     * 函数的参数为 ( id, field, value, success, error )
     * @param table
     * @returns {*}
     */
    function getSaver(table) {
        var on = table.attr("onsave");
        if (on) {
            eval("var save=" + on);
            return save;
        }
        var url = getUrl(table);
        // 一个默认的基于 jax 的保存
        return function (id, field, newValue, error) {
            var keyField = getKeyField(table); // 表的主键字段
            var data = {};
            data[keyField] = id;
            data[field] = newValue;

            $.ajax({
                type: 'PUT',
                url: url,
                data: data,
                success: function () {
                    error(null, table, field, newValue);
                },
                error: function (a, b, m) {
                    error(m, table, field, newValue);
                }
            });
            return true;
        };
    }

    /**
     * 返回处理失败事件的函数
     * @param table
     * @returns {*}
     */
    function getError(table) {
        var a=$(table).attr("onerror")
        if (a) {
            eval("var on=" + a);
            return on;
        }
        return function (msg) { // 定义一个默认的输出函数
            if (msg) alert(msg);
        }
    }

    function getConfirm(table, th) {
        var on = th.attr("confirm");
        if (!on) on = table.attr("confirm");
        if (!on) on = function () {
            return true;
        };
        eval("var func=" + on);
        return func;
    }

// 给单元格绑定双击事件,改为可修改的
    function pluginEditable(options) {
        var table = options.table;
        var th = options.th;
        var tr = options.tr;
        var td = options.td;

        // 防止多次绑定
        if (td.attr("editable")) return;
        td.attr("editable", true);

        td.dblclick(function () {
            var save = getSaver(table);
            var error = getError(table);

            var id = td.parent().attr("key");
            var keyField = getKeyField(table); // 表的主键字段
            var field = th.attr("field");

            var oldValue = td.attr("val");
            var oldText = td.html();

            var confirm = getConfirm(table, th);
            if (th.hasClass("boolean")) {
                // 对于布尔类型，双击直接更改值
                var b = (oldValue == 1) || ('true' == oldValue);
                b = confirm({
                    table: table,
                    th: th,
                    tr: tr,
                    td: td,
                    type: "boolean",
                    oldText: oldText,
                    oldValue: b,
                    newValue: !b
                });
                if (!b) return;
                save(id, d.field, !b, error);
                td.attr("val", !oldValue);
                td.html(!oldValue);
                var bl = cellPlugins["boolean"];    // 布尔插件
                $.each(bl, function (_, i) {
                    i(table, th, td);
                });
            } else if (th.attr("ref")) {  // 如果是引用类型
                $.cacheable(th.attr("ref"), function (ra) {
                    var map = {};
                    ra.forEach(function (i) {
                        var key = i[th.attr("keyField")];
                        map[key] = i[th.attr("valueField")];
                    });

                    if ($.isEmptyObject(map)) return;
                    editTdWithSelect(th, td, map, function (value) {
                        b = confirm({
                            table: table,
                            th: th,
                            tr: tr,
                            td: td,
                            type: "reference",
                            field: field,
                            title: $.trim(th.html()),
                            oldText: oldText,
                            oldValue: oldValue,
                            newValue: value,
                            newText: map[value]
                        });
                        if (b)
                            save(id, field, value, error);
                        else
                            td.html(oldText);
                    });
                });
            } else {
                td.editTdWithInput(function (value) {
                    b = confirm({
                        table: table,
                        th: th,
                        tr: tr,
                        td: td,
                        type: "text",
                        field: field,
                        title: $.trim(th.html()),
                        oldText: oldText,
                        oldValue: oldText,
                        newValue: value,
                        newText: value
                    });
                    if (b) {
                        save(id, field, value, error);
                    } else {
                        td.html(oldText);
                    }
                });
            }
        });
    }

    $.appendCellPlugin("editable", pluginEditable);

    function pluginActive(options) {
        var table = options.table;
        var tr = options.tr;
        var td = options.td;
        var th = options.th;
        var save = getSaver(table);
        var activeLine;

        var keyField = getKeyField(table); // 表的主键字段
        var id = options.row[keyField];
        var onClick = function (active) {
            // ( id, field, value, success, error )
            field = th.attr("field");
            if (!field) field = "active";
            save(id, field, active, function () {
                activeLine(tr, active, td);
            });
        };

        activeLine = function (tr, active, td) {
            var a = $("<a>");
            a.attr("href", "#");
            a.click(function () {
                onClick(!active);
            });
            if (active) {
                tr.removeClass("disabled");
                a.html("禁用");
                td.html(a);
            } else {
                tr.addClass("disabled");
                a.html("启用");
                td.html(a);
            }
        };

        var del = $("<a>").attr("href", "#").html("删除");
        var url = getUrl(table);
        // 一个默认的基于 jax 的保存
        var delData = {};
        delData[keyField] = id;

        del.click(function () {
            $.ajax({
                type: 'DELETE',
                url: url,
                data: delData,
                contentType: "application/x-www-form-urlencoded",
                success: function () {
                    tr.remove();
                }
            });
        });

        if (th.hasClass("action-active")) { // 激活操作
            var field = th.attr("field");
            if (!field) field = "active";
            var v = options.row[field];
            activeLine(tr, v, td);

            if (th.hasClass("action-delete") && !v) { // 同时有激活、删除的情况下，禁用的才允许删除
                td.append(" | ");
                td.append(del);
            }
        } else if (th.hasClass("action-delete")) {

        }
    }

    $.appendCellPlugin("action", pluginActive);
})(jQuery);
