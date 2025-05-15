document.getElementById("btnCalcular").addEventListener("click", function () {
    let valor = parseFloat(document.getElementById("valor").value);
    let parcelas = parseInt(document.getElementById("parcelas").value);
    let tipo = document.getElementById("tipo").value;

    // Definir taxa de juros e nome correspondente ao tipo de empréstimo
    let taxaJuros = tipo === "consignado" ? 0.015 : 0.035;
    let nomeEmprestimo = tipo === "consignado" ? "Consignado" : "Pessoal";

    // Verificar se é Consignado e validar mínimo de parcelas
    if (tipo === "consignado" && parcelas < 8) {
        alert("O empréstimo consignado exige pelo menos 8 parcelas.");
        return;
    }

    // Exibir tipo de empréstimo selecionado
    document.getElementById("tipoSelecionado").innerText = nomeEmprestimo;

    // Validar entrada
    if (isNaN(valor) || isNaN(parcelas) || valor <= 0 || parcelas <= 0) {
        alert("Por favor, insira valores válidos.");
        return;
    }

    // Calcular valor final com juros compostos
    let valorFinal = valor * Math.pow(1 + taxaJuros, parcelas);

    // Exibir resultados na área correta
    // Atualizar exibição da taxa de juros na página
    document.getElementById("taxaJuros").textContent = (taxaJuros * 100).toFixed(1) + "% a.m.";
    document.getElementById("total").innerText = "R$ " + valorFinal.toFixed(2);
    document.getElementById("parcelasResultado").innerText = parcelas.toFixed(2);
    document.getElementById("valorParcelas").innerText = "R$ " + (valorFinal / parcelas).toFixed(2);// Limitando as casas depois da virgula em duas.
    document.getElementById("juros").innerText = (taxaJuros * 100).toFixed(1) + "% a.m.";
});