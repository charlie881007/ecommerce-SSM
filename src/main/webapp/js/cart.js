function confirmRemoveClosedListings() {
    const confirmed = confirm("確定要移除所有已下架的商品嗎");
    if (confirmed) {
        removeClosedListings();
    }
}

function removeClosedListings() {
    $.ajax({
        type: "POST",
        url: contextPath + "/cart/removeClosed",
        success: function (data, textStatus) {
            if (data.success === true) {
                // 移除成功，刷新頁面
                window.location.replace(document.URL)
            } else if (data.success === false) {
                alert("伺服器發生錯誤")
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

function confirmChoice(listingId) {
    const confirmed = confirm("確定要移除此商品嗎");

    if (confirmed) {
        removeItem(listingId);
    }
}

function removeItem(listingId) {
    $.ajax({
        type: "POST",
        url: contextPath + "/cart/remove",
        dataType: "JSON",
        data: {
            listingId: listingId,
        },
        success: function (data, textStatus) {
            // 修改成功，刷新頁面
            if (data.success === true) {
                window.location.replace(document.URL);
            } else {
                alert(data.msg);
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

// Function to update total price
function updateTotal() {
    const subtotals = document.querySelectorAll('.subtotal');
    let total = 0;
    subtotals.forEach(subtotal => {
        total += parseFloat(subtotal.innerText.slice(3));
    });
    const totalElement = document.querySelectorAll('.total')[1];
    totalElement.innerText = '總金額：';
    totalElement.innerText = 'NT$' + total;
}

function notifyServer(listingId, quantity) {
    let isSuccess = false;
    $.ajax({
        method: "post",
        async: false,
        url: contextPath + "/cart/revise",
        data: {listingId: listingId, quantity: quantity},
        success: function (data, textStatus) {
            // 新增成功
            isSuccess = true;
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
    return isSuccess;
}

$(function () {
    const quantityInputs = document.querySelectorAll('input[type="number"]:not(.closed)');
    quantityInputs.forEach(input => {
        input.addEventListener('focus', (event) => {
            input.oldValue = input.value;
        });

        input.addEventListener('change', (e) => {
            const row = input.parentNode.parentNode;
            const listingId = input.getAttribute("id").slice(8);
            const price = parseFloat(row.querySelector('td:nth-of-type(2)').innerText.slice(3));
            const quantity = parseInt(input.value);
            const subtotal = row.querySelector('td:nth-of-type(5)');


            const isSuccess = notifyServer(listingId, quantity);

            if (isSuccess) {
                subtotal.innerText = 'NT$' + (price * quantity);
                updateTotal();
            } else {
                input.value = input.oldValue;
            }
        });
    });
});