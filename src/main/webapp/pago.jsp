<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<%@page import="Modelo.entidades.Producto" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Pagar - Holly's Baking</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f8f5f2; }
        .qr-container {
            background: white;
            border-radius: 20px;
            padding: 40px;
            text-align: center;
            box-shadow: 0 5px 20px rgba(0,0,0,0.1);
            max-width: 500px;
            margin: 0 auto;
        }
        .yape-color { color: #742284; }
        .yape-bg { background-color: #742284; color: white; }
        .monto-total {
            font-size: 2.5rem;
            font-weight: bold;
            color: #742284;
        }
        .btn-yape {
            background-color: #742284;
            color: white;
            border: none;
            border-radius: 25px;
            padding: 15px 40px;
            font-size: 1.2rem;
            width: 100%;
        }
        .btn-yape:hover { background-color: #5a1a6b; color: white; }
        .qr-placeholder {
            width: 250px;
            height: 250px;
            background: #f0f0f0;
            border-radius: 15px;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 20px auto;
            border: 3px dashed #742284;
        }
    </style>
</head>
<body>

<nav class="navbar navbar-expand-lg" style="background-color: #6F4E37;">
    <div class="container">
        <a class="navbar-brand text-white" href="CatalogoServlet">🧁 Holly's Baking</a>
    </div>
</nav>

<div class="container py-5">
    <div class="qr-container">
        <h3 class="yape-color mb-3">📱 Pago con Yape</h3>
        
        <%
            Double total = (Double) request.getAttribute("total");
            List<Producto> carrito = (List<Producto>) session.getAttribute("carrito");
            if (total == null) total = 0.0;
        %>
        
        <p class="text-muted">Total a pagar:</p>
        <div class="monto-total">S/ <%= String.format("%.2f", total) %></div>
        
        <div class="qr-placeholder">
            <!-- Aquí puedes poner un QR estático o generado -->
            <div style="text-align: center;">
                <div style="font-size: 4rem;">📲</div>
                <p class="yape-color mt-2"><strong>Escanea para pagar</strong></p>
            </div>
        </div>
        
        <div class="alert alert-info">
            <strong>📋 Instrucciones:</strong><br>
            1. Abre Yape en tu celular<br>
            2. Escanea el QR o busca <strong>Holly's Baking</strong><br>
            3. Ingresa el monto exacto: <strong>S/ <%= String.format("%.2f", total) %></strong><br>
            4. Confirma el pago
        </div>
        
        <form action="ConfirmarPagoServlet" method="POST">
            <input type="hidden" name="total" value="<%= total %>">
            <input type="hidden" name="metodo" value="yape">
            <button type="submit" class="btn btn-yape">
                ✅ Ya realicé el pago
            </button>
        </form>
        
        <a href="CarritoServlet" class="btn btn-outline-secondary mt-3 w-100">
            ← Volver al carrito
        </a>
    </div>
</div>

</body>
</html>