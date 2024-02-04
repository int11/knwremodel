window.onload = function(){
    loadscholarship()
}

function testdata(){
    $.ajax({
        url: '/scholarship/save',
        type: 'POST',
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({scholarshipId: 1, 
                              columns : [
                                "김치나베+우동사리",
                                "등심돈까스",
                                "제육당면덮밥\n(계란후라이추가)",
                                "제육당면덮밥",
                                "오므라ㅣ스",
                                "덮밥",
                                "백미\n사골떡\n함박스테이크",
                                "등심돈까스",
                                "유부우동(군만두)\n 추가밥",
                                "백미\n사골떡\n함박스테이크",
                                "등심돈까스",
                                "치즈라면"]}),
        success: function (response) {
            console.log(response)
        },
        error: function (data) {
            alert(data.responseText); // 에러 발생 시 콘솔에 출력
        }
    });

    setTimeout(function () {
        window.location.href = window.location.href;
    }, 50);
}

function loadscholarship(){
    $.ajax({
        url: '/scholarship/request',
        type: 'POST',
        contentType: "application/json",
        dataType: "json",
        success: function (response) {
            console.log(response)

            let table = document.getElementById("m")

            let tr = table.insertRow();
            for(let item in response[0]){
                

                if (item == "columns"){
                    for (let i = 0; i < response[0][item].length; i++){
                        let th = tr.insertCell();
                        th.innerText = i;
                    }
                }else{
                    let th = tr.insertCell();
                    th.innerText = item; 
                }
            }
        
            
            for (let index in response){
                let tr = table.insertRow();
                let item = response[index];
                for(let key in item){
                    if (key == "columns"){
                        for (let i = 0; i < item[key].length; i++){
                            let th = tr.insertCell();
                            th.innerText = item[key][i];
                        }
                    }else{
                        let td = tr.insertCell();
                        td.innerText = item[key]; 
                    }
                    
                }
            }

        },
        error: function (data) {
            alert(data.responseText); // 에러 발생 시 콘솔에 출력
        }
        });
}

