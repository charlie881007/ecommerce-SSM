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
        success: function (data) {
            if (data.success === true) {
                // 取消成功
                alert("取消成功");
                window.location.replace(document.URL)
            } else {
                alert("取消失敗，請洽詢客服")
            }
        },
        error: function (xhr, textStatus) {
            if (xhr.status === 400) {
                alert("參數錯誤")
            } else if (xhr.status === 401) {
                // 尚未登入
                window.location.replace(contextPath + "/login?redirect=" + window.location.href);
            } else if (xhr.status === 500) {
                alert("伺服器錯誤");
            }
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
