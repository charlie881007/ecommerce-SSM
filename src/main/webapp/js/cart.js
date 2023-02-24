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
        success: function () {
            window.location.replace(contextPath + "/cart");
        },
        error: function () {
            alert("刪除失敗");
            window.location.replace(contextPath + "/cart");
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
        data: {
            listingId: listingId,
        },
        dataType: "json",
        success: function (data, textStatus) {
            if (data.msg === "redirect") {
                // 尚未登入的話，後端會傳一個status code 200的json，其中msg是redirect，此時手動執行跳轉
                window.location.replace(contextPath + data.url);
            } else {
                // 刷新購物車頁面
                window.location.replace(document.URL);
            }
        },
        error: function (xhr, textStatus) {
            alert(JSON.parse(xhr.responseText).msg);
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
        dataType: "json",
        success: function () {
            isSuccess = true
        },
        error: function () {
            isSuccess = false
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