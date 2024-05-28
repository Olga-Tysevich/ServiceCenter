function createServiceCenterAccount() {
    let accountId = $('#account-id').val();
    let role = 'SERVICE_CENTER';
    let isActive = true;
    let serviceCenterId = $('#service-center-container-id option:selected').val();
    let email = $('#email-id').val();
    let userName = $('#user-name-id').val();
    let userSurname = $('#user-surname-id').val();
    let password = $('#password-id').val();
    let passwordConfirm = $('#password-confirm-id').val();

    let data = {
        id: accountId,
        serviceCenterId: serviceCenterId,
        role: role,
        email: email,
        userName: userName,
        userSurname: userSurname,
        password: password,
        passwordConfirm: passwordConfirm,
        isActive: isActive
    };

    $.ajax({
        url: '/accounts-rest/create-service-center-account',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (response) {
            window.location.href = '/accounts';
        },
        error: function (xhr, status, error) {
            let errorBlock = $('#error');
            errorBlock.text(xhr.responseText);
            errorBlock.css('color', 'RED');
        }
    });
}