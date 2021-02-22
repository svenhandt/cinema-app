
$(document).ready(function () {

    $('.seatCheckbox').click(function() {
        if(this.checked){
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: '/booking/addSeat',
                data: $(this).attr('id'),
                success: function(data) {
                    $("#totalPrice").text("Gesamtsumme: " + data.totalPriceFormatted);
                    $("#totalPrice").show();
                    $("#bookingButton").show();
                }
            });

        }
        if(!(this.checked)){
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: '/booking/removeSeat',
                data: $(this).attr('id'),
                success: function(data) {
                    if(data.totalPriceFormatted == null)
                    {
                        $("#totalPrice").hide();
                        $("#bookingButton").hide();
                        $("#totalPrice").text("");
                    }
                    else
                    {
                        $("#totalPrice").text("Gesamtsumme: " + data.totalPriceFormatted);
                    }
                }
            });

        }
    });

});
