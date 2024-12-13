function checkExistUsername(username) {
    return fetch(`./auth?mode=checkExist&username=${username}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        }
    }).then(resp => resp.json()).then(data => data.result);
}

export {checkExistUsername}