;

(function ($) {
    var cache = {};
    var cellPlugins = {};

    $.extend({
        // 全局缓冲 ajax 获取的表数据
        cacheable: function (name, fn) {
            var v = cache[name];
            if (v) {
                fn(v);
            } else {
                $.ajax({
                    type: 'GET',
                    url: name,
                    contentType: "application/json",
                    dataType: "json",
                    success: function (ra) {
                        cache[name] = ra;
                        fn(ra);
                    }
                });
            }
        },
        // 添加一个插件
        appendCellPlugin: function (className, func) {
            var p = cellPlugins[className];
            if (p)
                p.push(func);
            else
                cellPlugins[className] = [func];
        }
    });

    $.fn.createHead = function (options) {
        var table = $(this);
        var tr = $('<thead>').append("<tr>");

        $.each(options.columns, function (_, i) {
            var th = $("<th>");
            if (i.class) th.attr("class", i.class);
            if (i.field) th.attr("field", i.field);
            if (i.onCell) th.attr("onCell", i.onCell);

            th.html(i.title);
            tr.append(th);
        });
        table.append(tr);
    };

    /**
     * 通过行数据，给表格增加一行
     * @param row   行数据
     */
    $.fn.appendRow = function (row) {
        var table = $(this);
        var keyField = getKeyField(table);   // 表的主键字段

        var tr = $('<tr>');
        table.append(tr);

        $.data(tr, "data", row);
        var rowClass = table.attr("rowClass");
        if (rowClass) tr.addClass(rowClass);
        tr.attr("key", row[keyField]);  // 为行增加主键
        table.find("thead tr:last th").each(function (_, i) {
            var th = $(i);
            var field = $(th).attr("field");
            var _onCell = th.attr("onCell");
            var td = $("<td>");
            tr.append(td);

            if (field) td.html(row[field]);

            th = $(th);

            var cls = th.attr("cellClass");
            if (cls) {
                td.addClass(cls);
            }

            var options = {
                row: row,
                table: table,
                th: th,
                tr: tr,
                td: td
            };

            // 执行单元格插件
            for (var x in cellPlugins) {
                if (th.hasClass(x)) {
                    $.each(cellPlugins[x], function (_, o) {
                        o(options);
                    });
                }
            }

            if (_onCell) {
                eval("var func=" + _onCell);
                func({row: row, table: table, th: th, tr: tr, td: td});
            }
        });

        var app = true;
        // onRow 事件允许写在 table 和 thead 上
        var _onRow = table.attr("onRow");
        if (!_onRow) {
            var head = table.find("thead");  // 标题行
            _onRow = head.attr("onRow");
        }
        if (_onRow) {
            eval("var func=" + _onRow);
            if (func) app = func({table: table, tr: tr, row: row});
        }
        if (app) table.append(tr);
    };

    function getKeyField(table) {
        var keyField = table.attr("keyField");   // 表的主键字段
        return keyField ? keyField : "id";
    }

    /**
     * 填充表
     * @param options
     * @returns {*}
     */
    $.fn.jaxShow = function (options) {
        var data;
        if(options instanceof Array)
            data=options;
        else
            data=options.data;

        $(this).each(function (_, x) {
            var table = $(x);
            table.find("tbody").empty();
            $.each(data, function (_, row) {
                table.appendRow(row, options);
            });
        });
    };

    /**
     * 通过 jax 异步请求，并且填充表
     * @param options
     * @returns {*}
     */
    $.fn.jaxLoad = function (options) {
        var data;
        if(options) {
            if (options instanceof Array)
                data = options;
            else
                data = options.data;
        }

        // 如果已经提供了数据
        if(data){
            return $(this).each(function (_, x) {
                var table = $(x);
                table.find("tbody").empty();
                $.each(data, function (_, row) {
                    table.appendRow(row, options);
                });
            });
        }

        return $(this).each(function (_, x) {
            var table = $(x);
            var url = table.attr("url");
            if (!url) url = table.attr("name");
            if (!url) url = table.attr("id");

            // 清空表
            table.find("tbody").empty();

            $.ajax({
                type: 'GET',
                url: url,
                data: (options ? options.restrict : null),
                dataType: "json",
                success: function (json) {
                    $.each(json, function (_, row) {
                        table.appendRow(row);
                    });
                    table.show();
                    table.trigger("onload");
                }
            });
        });
    }
})(jQuery);

