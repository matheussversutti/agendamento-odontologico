console.log("Arquivo auth.js carregado com sucesso!");

function login() {
    console.log("Função login() chamada");
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    console.log("Credenciais enviadas:", { username, password });

    fetch("http://localhost:8080/auth/login", { // Certifique-se de que a URL da API está correta
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password })
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error("Usuário ou senha inválidos");
        }
    })
    .then(data => {
        localStorage.setItem("token", data.token);
        console.log("Token armazenado:", data.token);
        window.location.href = "/admin/dashboard";
    })
    .catch(error => {
        console.error("Erro no login:", error);
        alert(error.message);
    });
}

function loadDashboard() {
    const token = localStorage.getItem("token");

    console.log("Token enviado:", token);

    fetch("http://localhost:8080/admin/dashboard", {
        method: "GET",
        headers: { 
            "Authorization": `Bearer ${token}` 
        }
    })
    .then(response => {
        if (response.ok) {
            return response.text();
        } else {
            throw new Error("Erro ao carregar o dashboard");
        }
    })
    .then(data => {
        document.body.innerHTML = data;
    })
    .catch(error => {
        console.error("Erro ao carregar o dashboard:", error);
        window.location.href = "/login";
    });
}
