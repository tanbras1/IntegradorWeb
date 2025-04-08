document.getElementById("cadastroForm").addEventListener("submit", function(event) {
    event.preventDefault(); // Impede o envio padrão do formulário

    // Obter valores dos inputs
    let nome = document.getElementById("nomeCliente");
    let dataNascimento = document.getElementById("dataNascimento");
    let endereco = document.getElementById("endereco");
    let email = document.getElementById("email");
    let tipo = document.querySelector('input[name="tipo"]:checked'); // Verifica qual radio foi selecionado

    // Validar se todos os campos foram preenchidos corretamente
    let camposObrigatorios = [nome, dataNascimento, endereco, email];

    let validacao = true;
    
    camposObrigatorios.forEach(campo => {
        if (!campo.value.trim()) {
            campo.classList.add("is-invalid"); // Adiciona borda vermelha no campo vazio
            validacao = false;
        } else {
            campo.classList.remove("is-invalid"); // Remove erro se preenchido corretamente
        }
    });

    if (!tipo) {
        alert("Selecione um tipo de serviço antes de enviar.");
        return;
    }

    if (!validacao) {
        alert("Por favor, preencha todos os campos corretamente.");
        return;
    }

    // Exibir alerta de sucesso
    alert("Cliente cadastrado com sucesso!");

    // Limpar os campos do formulário
    document.getElementById("cadastroForm").reset();
});