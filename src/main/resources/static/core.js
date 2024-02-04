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


function createTable(jsonlist, table) {
    table.replaceChildren()
    let tr = table.insertRow();
    for(let item in jsonlist[0]){
        let th = tr.insertCell();
        th.innerText = item; 
    }

    for (let index in jsonlist){
        let tr = table.insertRow();
        tr.id = index
        let item = jsonlist[index];
        for(let key in item){
            let td = tr.insertCell();
            td.class = key;

            if (key == "isCheckLike"){
                let button = document.createElement("button");
                td.appendChild(button);
                button.onclick = function(){clickLike(item["id"], this);};
                if (item[key] == false){
                    button.innerText = "좋아요";
                }else{
                    button.innerText = "좋아요 취소";
                }

            }else if(key == "id"){
                let a = document.createElement("a");
                td.appendChild(a);
                a.href = "/read/" + item[key]
                a.innerText = item[key]; 
            }
            else{
                td.innerText = item[key]; 
            }
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

