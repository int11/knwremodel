// jquery import 코드
var script = document.createElement('script');
script.src = "https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"; // Check https://jquery.com/ for the current version
document.getElementsByTagName('head')[0].appendChild(script);

function request(
    url,
    data,
    successfunction,
    errorfunction = function(data){alert(data.responseText)}
) {
    $.ajax({
        url: url,
        type: 'POST',
        contentType: "application/json",
        data: JSON.stringify(data),
        success: successfunction,
        error: errorfunction
    });
}


function createTable(
    jsonlist,
    table,
    scheduleFunction=function (item, key, cell) {
        if (key == "isCheckLike"){
            let button = $("<button>");
            cell.append(button);
            button.click(function(){clickLike(item["id"], this);});
            button.text((item[key] == false) ? "좋아요" : "좋아요 취소")
        }else if(key == "id"){
            $(cell).append(`<a href="/read/${item[key]}">${item[key]}</a>`)
        }
        else{
            cell.text(item[key]);
        }
    }
) {
    if (!Array.isArray(jsonlist)) {
        // If it's not an array, wrap it in an array
        jsonlist = [jsonlist];
    }

    $(table).empty();

    let headerRow = $("<tr>");
    $(table).append(headerRow);
    for(let key in jsonlist[0]){
        headerRow.append(`<th>${key}</th>`);
    }

    for (let index in jsonlist){
        let row = $("<tr>");
        row.attr("id", index);
        $(table).append(row);
        let item = jsonlist[index];
        for(let key in item){
            let cell = $("<td>").addClass(key);
            row.append(cell);

            scheduleFunction(item, key, cell)

        }
    }
}
function clickLike(noticeId, self) {
    request(
        "/like/click",
        {noticeId: noticeId},

        function (data){
            let likecount = self.parentNode.previousElementSibling
            if (self.innerText == "좋아요"){
                likecount.innerText = parseInt(likecount.innerText) + 1;
                self.innerText = "좋아요 취소";
            }else{
                self.innerText = "좋아요";
                likecount.innerText = parseInt(likecount.innerText) - 1;
            }
        }
    )
}

function loadCurrentUser(){
    request(
        "/user/getCurrentUser",
        {},
        function (data) {
            if (data == false){
                $("#loginTrue").css("display", "none")
                $("#loginFalse").css("display", "block")
            }else{
                $("#loginTrue").css("display", "block")
                $("#loginFalse").css("display", "none")
                let table = $("#currentUserTable");
                createTable(data, table);
            }
        }
    )
}