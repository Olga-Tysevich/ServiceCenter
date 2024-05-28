function updateModel() {
    let isActive = $('input[name="isActive"]:checked').val();
    console.log("is active " + isActive)
    let id = $('#id').val();
    let brandId = $('#brand-id option:selected').val();
    let deviceTypeId = $('#device-type-id option:selected').val();
    let name = $('#model-name-id').val();

    let data = {
        id: id,
        brandId: brandId,
        deviceTypeId: deviceTypeId,
        name: name,
        isActive: isActive
    };

    $.ajax({
        url: '/models-rest/model-create-or-update',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (response) {
            window.location.href = '/models';
        },
        error: function (xhr, status, error) {
            let errorBlock = $('#error');
            errorBlock.text(xhr.responseText);
            errorBlock.css('color', 'RED');
        }
    });
}