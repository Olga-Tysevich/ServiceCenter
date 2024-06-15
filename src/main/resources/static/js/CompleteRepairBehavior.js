function addToRepair(button) {
    $('#error-quantity').hide();
    let row = $(button).closest('tr');
    let sparePartOrderId = row.find('td:nth-child(1)').text();
    let sparePartId = row.find('td:nth-child(3)').text();
    let sparePartName = row.find('td:nth-child(4)').text();
    let sparePartQuantity = parseInt(row.find('td:nth-child(5)').text());

    let newRow = `
            <div class="included-container" id="add-spare-part-id">
                 <div class="forms-container" id="order-container-id">
                      <div class="form-container order-form">
                        <div class="rc-form" id="spare-part-order_id">                        
                            <div class="f-input right">
                                 <button class="close" onclick="removeAddSparePartBlock()">X</button>
                            </div>
                        
                            <div style="display: none" id="spare-part-order-id">${sparePartOrderId}</div>
                            <div style="display: none" id="spare-part-id">${sparePartId}</div>
                        
                            <div class="f-input">
                                <div class="form-el">Запчасть:</div>
                                <p id="spare-part-name-id">${sparePartName}</p>
                            </div>    
                            <div class="f-input">
                                <label class="form-el">Количество:</label>
                                <input id="max-quantity-id" type="hidden" name="max_quantity" value="${sparePartQuantity}">
                                <input id="quantity-id" type="number" name="input_quantity" value="${sparePartQuantity}">
                            </div>   
                            
                            <div class="f-input">
                                 <button class="button" onclick="addSparePartToRepair()">Добавить</button>
                            </div>
                                                   
                        </div>
                      </div>
                 </div>
            </div>
        `;
    row.append(newRow);
}

function removeAddSparePartBlock() {
    $('#add-spare-part-id').remove();
}

function addSparePartToRepair() {
    let sparePartOrderId = parseInt($('#spare-part-order-id').text());
    let sparePartId = parseInt($('#spare-part-id').text());
    let sparePartName = $('#spare-part-name-id').text();
    let sparePartQuantity = parseInt($('#quantity-id').val());
    if (sparePartQuantity <= 0) {
        $('#error-quantity').text('Количество запчастей не может быть меньше одной!').show();
        removeAddSparePartBlock();
        return;
    }

    let maxSparePartQuantity = parseInt($('#max-quantity-id').val());
    if (sparePartQuantity > maxSparePartQuantity) {
        sparePartQuantity = maxSparePartQuantity;
    }

    let repairType = $('#repair-type-data-id');
    addRepairType();

    let sparePartAdded = false;

    repairType.find('tbody tr').each(function () {
        let existingSparePartOrderId = $(this).find('th:nth-child(1)').text();
        let existingSparePartId = $(this).find('th:nth-child(2)').text();
        let existingSparePartQuantityBlock = $(this).find('th:nth-child(4)');
        let existingSparePartQuantity = parseInt(existingSparePartQuantityBlock.text());

        if (parseInt(existingSparePartOrderId) === sparePartOrderId && parseInt(existingSparePartId) === sparePartId) {
            sparePartAdded = true;
            if (existingSparePartQuantity === maxSparePartQuantity || existingSparePartQuantity + sparePartQuantity > maxSparePartQuantity) {
                $('#error-quantity').text('Количество запчастей уже равно или превышает введенное количество!').show();
            } else {
                existingSparePartQuantityBlock.text(existingSparePartQuantity + sparePartQuantity);
            }
            return false;
        }
    });

    if (!sparePartAdded) {
        let newRow = `
            <tr>
                <th style="display: none">${sparePartOrderId}</th>
                <th style="display: none">${sparePartId}</th>
                <th>${sparePartName}</th>
                <th>${sparePartQuantity}</th>
                <th style="width: 10%">
                    <button class="button light" onclick="deleteSparePart(this)">Удалить</button>
                </th>
            </tr>
        `;

        repairType.find('tbody').append(newRow);
    }
    removeAddSparePartBlock();
}

function deleteSparePart(button) {
    button.closest('tr').remove();
}

function addRepairType() {
    let repairTypeDescription = $('#repair-type-description-id');
    let descriptionSelect = $('select[name="repairType"] option:selected').text();
    repairTypeDescription.text(descriptionSelect);
}

function completeRepair() {
    let repairId = parseInt($('#repairId').val());
    let repairTypeData = $('#repair-type-data-id');
    let repairTypeId = parseInt($('select[name="repairType"] option:selected').val());
    let spareParts = [];


    repairTypeData.find('tbody tr').each(function () {
        let orderId = parseInt($(this).find('th:nth-child(1)').text());
        let spareParId = parseInt($(this).find('th:nth-child(2)').text());
        let quantity = parseInt($(this).find('th:nth-child(4)').text());

        let repairSparePartItem = {
            actualRepairId: repairId,
            orderId: orderId,
            sparePartId: spareParId,
            quantity: quantity
        };

        spareParts.push(repairSparePartItem);
    });

    let data = {
        repairId: repairId,
        repairTypeId: repairTypeId,
        spareParts: spareParts
    };

    $.ajax({
        url: '/repairs-rest/complete-repair',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (response) {
            window.location.href = `/repairs/repair-page/${repairId}`;
        },
        error: function (xhr, status, error) {
            let errorBlock = $('#error-id');
            if (xhr.status === 400) {
                errorBlock.text(xhr.responseText);
            } else {
                let errorBlock = $('#error');
                errorBlock.text("Что-то пошло не так");
            }
            errorBlock.css('color', 'RED');
            errorBlock.show();
        }
    });
}


function getStockSpareParts() {

    let modelId = $('#modelId').val();
    $.ajax({
        url: '/stock-spare-part-rest/by-model-id/' + modelId,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            let stockSparePartsContainer = $('#stock-stare-parts-block-id').find('tbody');
            $.each(data, function (index, sparePart) {

                let tableRow = `
                    <tr>
                        <td class="order-id">${sparePart.orderId}</td>
                        <td>${sparePart.orderDate}</td>
                        <td style="display: none">${sparePart.id}"</td>
                        <td>${sparePart.name}</td>
                        <td>${sparePart.quantity}</td>
                        <td>
                            ${sparePart.repairNumber}"
                            <a href="/repairs/repair-page/${sparePart.repairId}">Перейти в ремонт</a>
                        </td>
                        <td>
                        <button class="button light" onclick="addToRepair(this)">Добавить в ремонт</button>
                        </td>
                    </tr>
                `;
                stockSparePartsContainer.append(tableRow);

            })

        },
        error: function () {
            $('#error_id').text('Нет доступных запчастей для данной модели!').show();
        }
    })
}
