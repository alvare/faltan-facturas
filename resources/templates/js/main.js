$(function(){
    $('#date').datepicker({
        dateFormat: "yy-mm-dd",
        onSelect: function(d, inst){
            if($('#date').datepicker('getDate').toString().split(' ')[0] == 'Thu'){
                $('#result').html('<img src="/images/loading.gif" width="25">');
                $.ajax({
                    url: "/facturas",
                    data: {date: d}
                }).done(function(data){
                    $('#result').html(data[1].name).css('color', 'black');
                    window.history.pushState({},'',  window.location.href.slice(0,1-window.location.pathname.length) + 'toca/' + d)
                }).fail(function(){
                    $('#result').html("Error Dog 404 Shit").css('color', 'red');
                });
            } else {
                $('#result').html('Not Jueves!');
            }
        }
    });
    if("{{name}}" != ""){
        $('#date').datepicker('setDate', "{{date}}")
    }
});
