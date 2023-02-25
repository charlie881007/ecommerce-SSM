let emailUsed = true;

function validateEmailFormat(email) {
    const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    return emailRegex.test(email);
}

function validateForm() {
    const email = document.getElementById("email").value;
    const code = document.getElementById("code").value;
    const password = document.getElementById("pw1").value;
    const confirmPassword = document.getElementById("pw2").value;
    const firstName = document.getElementById("first_name").value;
    const lastName = document.getElementById("last_name").value;
    const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,20}$/;
    const nameRegex = /^\S{1,20}$/
    let hasError = false;

    // var emailRegex = /^*$/;
    // var passwordRegex = /^*$/;
    // var nameRegex = /^*$/

    if (email === "") {
        document.getElementById("email_error").innerHTML = "*Email為必填欄位";
        hasError = true;
    } else if (validateEmailFormat(email)) {
        document.getElementById("email_error").innerHTML = "*Email格式錯誤";
        hasError = true;
    } else {
        document.getElementById("email_error").innerHTML = "";
    }

    if (code === "") {
        document.getElementById("code_error").innerHTML = "*驗證碼為必填欄位";
        hasError = true;
    }

    if (password === "") {
        document.getElementById("password_error").innerHTML = "*密碼為必填欄位";
        hasError = true;
    } else if (!passwordRegex.test(password)) {
        document.getElementById("password_error").innerHTML = "*密碼至少需要1個數字、1個字母、1個特殊符號，總長8到20字";
        hasError = true;
    } else {
        document.getElementById("password_error").innerHTML = "";
    }

    if (confirmPassword === "") {
        document.getElementById("confirm_password_error").innerHTML = "*確認密碼為必填欄位";
        hasError = true;
    } else if (password !== confirmPassword) {
        document.getElementById("confirm_password_error").innerHTML = "*密碼不符";
        hasError = true;
    } else {
        document.getElementById("confirm_password_error").innerHTML = "";
    }

    if (firstName === "") {
        document.getElementById("first_name_error").innerHTML = "*名為必填欄位";
        hasError = true;
    } else if (!nameRegex.test(firstName)) {
        document.getElementById("first_name_error").innerHTML = "名字長度1到20，不能包含特殊符號";
        hasError = true;
    } else {
        document.getElementById("first_name_error").innerHTML = "";
    }

    if (lastName === "") {
        document.getElementById("last_name_error").innerHTML = "*姓氏為必填欄位";
        hasError = true;
    } else if (!nameRegex.test(lastName)) {
        document.getElementById("last_name_error").innerHTML = "姓氏長度1到20，不能包含特殊符號";
        hasError = true;
    } else {
        document.getElementById("last_name_error").innerHTML = "";
    }

    return !hasError;
}

$(function () {
    $("#email").focusout(function () {
        const email = $("#email").val();
        const emailError = $("#email_error")[0];
        const emailCheck = $("#email_check")[0];

        if (!validateEmailFormat(email)) {
            document.getElementById("email_error").innerHTML = "*Email格式錯誤";
            document.getElementById("email_check").innerHTML = "";
            return;
        }

        $.ajax({
            type: "GET",
            url: contextPath + "/checkRegistered",
            data: {email: email},
            dataType: "JSON",
            success: function (data) {
                if (data.canUse === true) {
                    emailCheck.innerText = "此信箱可以使用";
                    emailError.innerText = "";
                    emailUsed = false;
                } else {
                    emailError.innerText = "此信箱已經註冊過羅";
                    emailCheck.innerText = "";
                    emailUsed = true;
                }
            },
            error: function () {

            }
        });
    });

    const sendCodeBtn = $("#sendCodeBtn")[0];
    sendCodeBtn.onclick = function () {
        const email = $("#email").val();

        if (!validateEmailFormat(email) || emailUsed) {
            return;
        }

        let sec = 60;
        this.disabled = true;

        let countdownTimer = setInterval(function () {
            if (sec === 0) {
                sendCodeBtn.disabled = false;
                sendCodeBtn.value = "發送";
                clearInterval(countdownTimer);
            } else {
                sendCodeBtn.value = "發送 " + --sec;
            }
        }, 1000);

        $.ajax({
            type: "POST",
            url: contextPath + "/verify/resend",
            data: {email: email},
        });
    };
})