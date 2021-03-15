$( document ).ajaxSend((event, xhr) => {
    if (security.csrf.value) {
        xhr.setRequestHeader(security.csrf.header, security.csrf.value);
    }
    if (security.accessToken) {
        xhr.setRequestHeader("Authorization", "Bearer " + security.accessToken);
    }
});

$( document ).ajaxSuccess((event, xhr, objects, data) => {
    const links = data["_links"];
    if (links) {
        if (links["up"]) {
            chachkie.up = () => chachkie._up(chachkie.root + "/up");
            $("#up-arrow").addClass("enabled")[0].onclick = chachkie.up;
        } else {
            delete chachkie.up;
            delete $("#up-arrow").removeClass("enabled")[0].onclick;
        }
        if (links["down"]) {
            chachkie.down = () => chachkie._down(chachkie.root + "/down");
            $("#down-arrow").addClass("enabled")[0].onclick = chachkie.down;
        } else {
            delete chachkie.down;
            delete $("#down-arrow").removeClass("enabled")[0].onclick;
        }
    }

    security.success(xhr);
});

$( document ).ajaxComplete((event, xhr) => {
    if (xhr.status === 401 || xhr.status === 403) {
        return security.authorize();
    }
});

const chachkie = {
    root: "http://localhost:8080/chachkie",
    read: () => $.get(chachkie.root, (data) => $("#chachkie").html(data.chachkie)),
    _up: (url) => $.post(url, (data) => $("#chachkie").html(data.chachkie)),
    _down: (url) => $.post(url, (data) => $("#chachkie").html(data.chachkie))
};

const security = {
    authorize: () => {
        location.reload();
    },
    csrf: {
        header: "x-csrf-token"
    },
    success: (xhr) => {
        security.csrf.value = xhr.getResponseHeader(security.csrf.header);
    }
};

$(() => {
    chachkie.read();
});