$(document).ready(function() {
    $('select[name="brandId"]').change(function() {
        let brandId = $(this).val();
        $.ajax({
            url: '/models-rest/by-brand/' + brandId,
            type: 'GET',
            dataType: 'json',
            success: function(data) {
                $('#error_id').text('Нет ослуживаемых моделей для выбранного бренда!').hide();
                $('select[name="modelId"]').empty();
                $.each(data, function(index, model) {
                    $('select[name="modelId"]').append('<option value="' + model.id + '">' + model.name + '</option>');
                });
            },
            error: function() {
                $('#error_id').text('Нет ослуживаемых моделей для выбранного бренда!').show();
                $('select[name="modelId"]').empty();
            }
        });
    });
});

$(document).ready(function () {
    $('#order_id').click(function () {
        let sparePartList = $('#spare-parts-container-id');
        if (sparePartList.find('option').length === 0) {
            let modelId = $('#model-id').val();
            console.log(modelId)
            $.ajax({
                url: '/spare-part-rest/by-model-id/' + modelId,
                type: 'GET',
                dataType: 'json',
                success: function (data) {
                    // $('#error_id').text('Нет доступных запчастей для данной модели!').hide();
                    let sparePartsContainer = $('#spare-parts-container-id');
                    $.each(data, function (index, sparePart) {
                        sparePartsContainer.append('<option value="' + sparePart.id + '">' + sparePart.name + '</option>');
                    })
                    $('#order-block-id').show();

                },
                error: function () {
                    $('#error_id').text('Нет доступных запчастей для данной модели!').show();
                }
            })
        } else {
            let orderForm = $('#order-block-id');
            orderForm.show();
        }
    })
})


