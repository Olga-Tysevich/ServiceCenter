$(document).ready(function() {
    $('.button.light').click(function() {
        $(this).next('.included-container').show();
    });
});

$(document).ready(function () {
    $('.close').click(function () {
        $(this).closest('.included-container').hide();
    })
})