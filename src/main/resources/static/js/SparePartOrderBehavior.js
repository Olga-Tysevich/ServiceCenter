$(document).ready(function () {
    $('#add-id').click(function () {
        let selectedPart = $('#spare-parts-container-id option:selected');
        let selectedPartId = selectedPart.val();
        let selectedPartName = selectedPart.text();
        let quantity = $('#quantity-id').val();

        let newRow = `
            <tr class="order-item">
                <td>${selectedPartName}</td>
                <td>${quantity}</td>
                <td style="display: none" class="spare-part-id-container">${selectedPartId}</td>
                <td>
                    <input class="delete_btn" type="button" value="-" id="delete-btn-id"/>
                </td>
            </tr>
            `;
        $('#order-body-id').append(newRow);
    });

    $('#order-body-id').on('click', '#delete-btn-id', function () {
        $(this).closest('tr').remove();
    });
});

function closeOrderForm() {
    let orderForm = $('#order-block-id');
    $('#message-block-id').show();
    orderForm.hide();
}

function createOrder() {
    let repairId = $('#id').val();
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
            showMessage(response, 'GREEN');
            let orderItems = $('#order-body-id');
            orderItems.find('tr').each(function () {
                $(this).remove();
            });
        },
        error: function (xhr, status, error) {
            if (xhr.status === 400) {
                let errorBlock = $('#message-id');
                errorBlock.text(xhr.responseText);
                errorBlock.css('color', 'RED');
                $('#message-block-id').show();
            } else {
                let errorBlock = $('#message-id');
                errorBlock.text("Что-то пошло не так");
                errorBlock.css('color', 'RED');
            }
        }
    });
}

function showMessage(message, color) {
    let messageContainer = $('#message-id');
    messageContainer.text(message);
    messageContainer.css('color', color);
    $('#message-block-id').show();
}