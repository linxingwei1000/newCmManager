/**
 索引插件
 */

(function ($) {
// 转化成索引表
// 查询索引表
    function queryMap(name, keyField, fn) {
        $.cacheable(name, function (data) {
            var d = {};
            $.each(data, function (_, i) {
                d[i[keyField]] = i;
            });
            fn(d);
        });
    }

    function pluginReference(options) {
        // 如果定义了引用
        var th = $(options.th);
        var td = $(options.td);
        var v = td.attr("val");
        if (!v) v = td.html();
        var reference = th.attr("ref");
        if (v != undefined && reference) {
            var keyField = th.attr("keyField");
            var valueField = th.attr("valueField");
            queryMap(reference, keyField, function (refMap) {
                var row = refMap[v];
                if (row) {
                    var x = row[valueField];
                    td.html(x ? x : "");
                }
            });
        } else {
            td.attr("val", null);
            td.html(v);
        }
    }

    $.appendCellPlugin("reference", pluginReference);
})(jQuery);
