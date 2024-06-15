function updateSparePart() {
    let pageNum = $('#pageNumId').val();
    let sortField = $('#sortFieldId').val();
    let sortDir = $('#sortDirId').val();
    let keyword = $('#keywordId').val();

    let isActive = $('input[name="isActive"]').val();
    let id = $('#id').val();
    let name = $('#sp-name').val();
    let modelsId = [];

    $('tr').each(function () {
        $(this).find('input[type="checkbox"][name="isChecked"]:checked').each(function () {
            let modelId = $(this).val();
            modelsId.push(modelId);
        });
    });

    let data = {
        pageNum: pageNum,
        sortField: sortField,
        sortDir: sortDir,
        keyword: keyword,
        id: id,
        name: name,
        modelIdList: modelsId,
        isActive: isActive
    };

    $.ajax({
        url: '/spare-part-rest/spare-part-create-or-update',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (response) {
            window.location.href = '/spare-parts';
        },
        error: function (xhr, status, error) {
            if (xhr.status === 400) {
                let errorBlock = $('#error');
                errorBlock.text(xhr.responseText);
                errorBlock.css('color', 'RED');
            } else {
                let errorBlock = $('#error');
                errorBlock.text("Что-то пошло не так");
                errorBlock.css('color', 'RED');
            }
        }
    });
}