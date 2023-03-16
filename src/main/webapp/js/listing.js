$(function () {
    const listingId = $("#listingId").val();
    const qtyInput = $("#qtyInput");
    const maxQty = $("#maxQty").innerText;
    qtyInput.onchange = function () {
        if (qtyInput.value > maxQty) {
            qtyInput.value = maxQty;
        }
    };

    $("#submitBtn").on("click", function () {
        const data = {
            listingId: listingId,
            quantity: qtyInput.val()
        };

        if (qtyInput.val() === 0 + ""){
            return;
        }

        $.ajax({
            type: "POST",
            url: contextPath + "/cart/add",
            data: data,
            success: function (data) {
                if (data.success === true) {
                    // 新增成功
                    alert("新增成功");
                } else {
                    if (data.msg === "duplicate item") {
                        alert("購物車已有此物品")
                    } else if (data.msg === "closed item") {
                        alert("此商品已下架")
                    } else if (data.msg === "insufficient item") {
                        alert("商品數量不足")
                    }
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
    })
})