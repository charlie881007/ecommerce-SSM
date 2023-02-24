function confirm_cancel(orderId) {
    const isConfirmed = confirm("確定要取消訂單嗎？")
    if (isConfirmed) {
        cancelOrder(orderId);
    }
}

function cancelOrder(orderId) {
    $.ajax({
        method: "post",
        async: false,
        url: contextPath + "/orders/" + orderId + "/cancel",
        data: {orderId: orderId},
        success: function () {
            window.location.replace(contextPath + "/orders");
        },
        error: function () {
            alert("取消失敗");
            window.location.replace(contextPath + "/orders");
        }
    });
}


$(
    function () {
        const cancelButtons = document.querySelectorAll('.cancel-btn');

        cancelButtons.forEach(button => {
                button.addEventListener('click', (event) => {
                    const orderId = button.parentElement.parentElement.querySelector("td").innerText;
                    confirm_cancel(orderId);
                })
            }
        );
    }
)
