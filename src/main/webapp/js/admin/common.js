function html([first, ...strings], ...values) {
    return values.reduce(
        (acc, cur) => acc.concat(cur, strings.shift()),
        [first]
    )
        .filter(x => x && x !== true || x === 0)
        .join('');
}

function checkExistUsername(username) {
    return fetch(`./auth?mode=checkExist&username=${username}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        }
    }).then(resp => resp.json()).then(data => data.result);
}

export {checkExistUsername, html}