const messageBlock = $('#message-block-id');
const orderBlock = $('#order-body-id');

$(document).ready(function () {
    $('#add-id').click(function () {
        messageBlock.hide();
        let selectedPart = $('#spare-parts-container-id option:selected');
        let selectedPartId = selectedPart.val();
        let selectedPartName = selectedPart.text();
        let quantity = $('#quantity-id').val();

        if (quantity <= 0) {
            $('#message-id').text('Количество не может быть меньше 1!')
            messageBlock.css('color', 'RED');
            messageBlock.show();
            return;
        }

        let test = orderBlock.find(`td:contains(${selectedPartName})`);
        if (test.length !== 0) {
            let quantityTemp = test.closest('td').closest('tr').find('.spare_part_quantity').text();
            let newQuantity = parseInt(quantityTemp) + parseInt(quantity);
            test.closest('tr').find('.spare_part_quantity').text(newQuantity);
            return;
        }

        let newRow = `
            <tr class="order-item">
                <td class="spare_part_name">${selectedPartName}</td>
                <td class="spare_part_quantity">${quantity}</td>
                <td style="display: none" class="spare-part-id-container">${selectedPartId}</td>
                <td>
                    <input class="delete_btn" type="button" value="-" id="delete-btn-id"/>
                </td>
            </tr>
            `;
        orderBlock.append(newRow);
    });

    orderBlock.on('click', '#delete-btn-id', function () {
        $(this).closest('tr').remove();
    });
});

function closeOrderForm() {
    let orderForm = $('#order-block-id');
    messageBlock.show();
    orderForm.hide();
}

function createOrder() {
    let repairId = $('#id').val();
    let repairNumber = $('#repairNumber').val();
    let orderItems = [];

    $('tr.order-item').each(function () {
        let name = $(this).find('td:nth-child(1)').text();
        let quantity = parseInt($(this).find('td:nth-child(2)').text());
        let id = parseInt($(this).find('td:nth-child(3)').text());
        let orderItem = {
            id: id,
            name: name,
            quantity: quantity
        };
        orderItems.push(orderItem);
    });


    let data = {
        repairId: repairId,
        orderItems: orderItems
    };

    $.ajax({
        url: '/spare-part-orders-rest/create-order',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (response) {
            orderBlock.find('tr').each(function () {
                $(this).remove();
            });
            window.location.href = '/spare-part-orders/show-repair-orders/' + repairId + '?repairNumber=' + repairNumber;
        },
        error: function (xhr, status, error) {
            if (xhr.status === 400) {
                let errorBlock = $('#message-id');
                errorBlock.text(xhr.responseText);
                errorBlock.css('color', 'RED');
                messageBlock.show();
            } else {
                let errorBlock = $('#message-id');
                errorBlock.text("Ошибка сервера, попробуйте позднее");
                errorBlock.css('color', 'RED');
            }
        }
    });
}
