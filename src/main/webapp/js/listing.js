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

        $.ajax({
            type: "POST",
            url: contextPath + "/cart/add",
            data: data,
            dataType: "json",
            success: function (data, textStatus) {
                if (data.msg === "redirect") {
                    // 尚未登入
                    window.location.replace(contextPath + data.url);
                } else {
                    // 新增成功
                    alert(data.msg);
                }
            },
            error: function (xhr, textStatus) {
                const response = JSON.parse(xhr.responseText);
                alert(response.msg);
            }
        });
    })
})