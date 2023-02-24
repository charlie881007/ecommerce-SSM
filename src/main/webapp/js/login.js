//設定cookie
function setCookie(name, value, day) {
    const date = new Date();
    date.setDate(date.getDate() + day);
    document.cookie = name + '=' + value + ';expires=' + date;
}

//獲取cookie
function getCookie(name) {
    const reg = RegExp(name + '=([^;]+)');
    const arr = document.cookie.match(reg);
    if (arr) {
        return arr[1];
    } else {
        return '';
    }
}

//刪除cookie
function delCookie(name) {
    setCookie(name, null, -1);
}

function validateForm() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const remember = document.getElementById("remember");


    // 判斷要不要將信箱與密碼放入Cookie
    if (remember.checked) {
        setCookie("email", email, 7);
        setCookie("password", password, 7);
    } else {
        delCookie("email");
        delCookie("password");
    }


    // 前端validate
    if (email === "") {
        document.getElementById("email_error").innerHTML = "*Email為必填欄位";
        hasError = true;
    } else if (!emailRegex.test(email)) {
        document.getElementById("email_error").innerHTML = "*Email格式錯誤";
        hasError = true;
    } else {
        document.getElementById("email_error").innerHTML = "";
    }

    if (password === "") {
        document.getElementById("password_error").innerHTML = "*密碼為必填欄位";
        hasError = true;
    } else {
        document.getElementById("password_error").innerHTML = "";
    }

    if (!hasError) {

    }

    return !hasError;
}

window.onload = function () {
    const email = document.getElementById('email');
    const password = document.getElementById('password');
    const remember = document.getElementById('remember');
    //頁面初始化時，如果帳號密碼cookie存在則填充
    if (getCookie('email') && getCookie('password')) {
        email.value = getCookie('email');
        password.value = getCookie('password');
        remember.checked = true;
    }

    remember.onchange = function () {
        if (!this.checked) {
            email.value = "";
            password.value = "";
        }
    };
};