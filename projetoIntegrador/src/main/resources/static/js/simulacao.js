document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("simulacaoForm");

    if (form) {
        form.addEventListener("submit", function (event) {
            if (!validarCampos()) {
                event.preventDefault(); // Impede envio se os dados forem inválidos
            }
        });
    }

    // Valida os campos antes de enviar 
    function validarCampos() {
        let valor = document.getElementById("valor").value;
        let parcelas = document.getElementById("parcelas").value;

        if (valor <= 0 || parcelas <= 2) {
            alert("Insira valores válidos!");
            return false; // Bloqueia envio se os valores forem incorretos
        }
        return true;
    }
});






