function createClientes() {
    var name = document.querySelector('input[name="name"]').value;
    var sobrenome = document.querySelector('input[name="sobrenome"]').value;
    var documento = document.querySelector('input[name="documento"]').value;

    var newClient = {
        nome: name,
        sobrenome: sobrenome,
        documento: documento
    };

    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", "http://localhost:8080/clientes", true);
    xhttp.setRequestHeader('Content-Type', 'application/json');

    xhttp.onload = function () {
        if (xhttp.readyState == 4) {
            if (xhttp.status == 200) {
                displayMessage("Cliente cadastrado com sucesso.", "success");
                hideClientesTable(); 
            } else {
                displayMessage("Erro ao cadastrar o cliente. Verifique os campos e tente novamente.", "error");
            }
        }
    }

    xhttp.send(JSON.stringify(newClient));
}

function hideClientesTable() {
    var clientesTable = document.getElementById("clientes-table");
    clientesTable.style.display = "none";
}


function getClientes() {
    var xhttp = new XMLHttpRequest();

    xhttp.open("GET", "http://localhost:8080/clientes", true);
    xhttp.setRequestHeader('Content-Type', 'application/json');

    xhttp.onload = function () {
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            var clients = JSON.parse(xhttp.responseText);
            var clientList = document.getElementById("clientList");
            var clientesTable = document.getElementById("clientes-table");

            
            clientList.innerHTML = "";

            
            clients.forEach(function (client) {
                var row = clientList.insertRow();
                var idCell = row.insertCell(0);
                var nomeCell = row.insertCell(1);
                var sobrenomeCell = row.insertCell(2);
                var documentoCell = row.insertCell(3);

                idCell.innerHTML = client.id;
                nomeCell.innerHTML = client.nome;
                sobrenomeCell.innerHTML = client.sobrenome;
                documentoCell.innerHTML = client.documento;
            });

            
            clientesTable.style.display = "block";
        }
    }

    xhttp.send();
}



function displayMessage(message, type) {
    var messageElement = document.getElementById("message");
    messageElement.innerText = message;
    messageElement.classList.remove("success", "error");
    messageElement.classList.add(type);

    
    setTimeout(function () {
        messageElement.innerText = "";
    }, 3000); 
}
