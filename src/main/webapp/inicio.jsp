<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="Latin1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Parque de Diversões</title>
        <link rel="stylesheet" href="estilo/inicio.css">
        <style>
            /* Seus estilos inline... */
            .card {
                background-color: #e6f2ff;
                padding: 20px;
                border-radius: 10px;
                width: 250px;
                box-shadow: 0 2px 6px rgba(0,0,0,0.1);
                background-size: cover;        /* Faz a imagem preencher todo o card */
                background-position: center;   /* Centraliza a imagem */
                background-repeat: no-repeat;  /* Evita repetição da imagem */
                transition: transform 0.2s ease;
                cursor: pointer;
                text-align: center;
            }

            .card:hover {
                transform: scale(1.05);
            }

            /* Remove sublinhado e mantém cor herdada dos links */
            .cards a {
                text-decoration: none;
                color: inherit;
            }

            .cards {
                display: flex;
                justify-content: center;
                gap: 20px;
                flex-wrap: wrap;
            }
        </style>
    </head>
    <body>
        <header>
            <%@include file="menuParque.jsp"%>
        </header>
        <main>
            <section class="hero">
                <h1>Bem-vindo ao Parque de Diversões!</h1>
                <p>O lugar perfeito para diversão e aventuras emocionantes.</p>
            </section>

            <section class="destaques">
                <h2>Destaques do Parque</h2>
                <div class="cards">
                    <a href="CadastroCompras.jsp">
                        <div id="card1" class="card">
                            <h3>Montanha-Russa</h3>
                            <p>Velocidade e adrenalina em um só brinquedo!</p>
                        </div>
                    </a>

                    <a href="CadastroCompras.jsp">
                        <div id="card2" class="card">
                            <h3>Baile de Máscaras</h3>
                            <p>Sinta a emoção de ser livre por uma noite!</p>
                        </div>
                    </a>

                    <a href="CadastroCompras.jsp">
                        <div id="card3" class="card">
                            <h3>Lanches</h3>
                            <p>Onde quer que vá, um cardápio apropriado você terá!</p>
                        </div>
                    </a>
                </div>
            </section>  
        </main>

        <footer>
            <p>&copy; 2025 Parque de Diversões. Todos os direitos reservados.</p>
        </footer>
    </body>
</html>
