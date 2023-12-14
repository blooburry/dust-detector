document.addEventListener('DOMContentLoaded', function () {
    // Simuleer het sturen van een HTTP-verzoek naar de server
    fetch('/api/data') // Stel de juiste URL in voor je server
        .then(response => response.json())
        .then(data => {
            // Toon de serverreactie op de pagina
            const responseElement = document.getElementById('server-response');
            responseElement.textContent = Serverreactie: ${data.message};
        })
        .catch(error => console.error('Fout bij het ophalen van servergegevens:', error));
});
