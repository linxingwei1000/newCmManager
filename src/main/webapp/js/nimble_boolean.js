;

(function ($) {
    /**
     * 布尔型字段插件
     */
    function pluginBoolean( options ) {
        var th = options.th;
        var td = options.td;
        var v = td.html();
        var b = (v == 1) || ('true' == v);
        td.attr("val", b);

        var on = th.attr("booleanValue");
        if (on) {
            eval("var func=" + on);
            td.html(func(b));
        } else {
            var trueValue = th.attr("trueValue");
            if (!trueValue) trueValue = "是";
            var falseValue = th.attr("falseValue");
            if (!falseValue) falseValue = "否";
            td.html(b ? trueValue : falseValue);
        }
    }

    $.appendCellPlugin("boolean", pluginBoolean);
})(jQuery);
