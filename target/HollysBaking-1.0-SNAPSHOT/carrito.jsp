<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<%@page import="Modelo.entidades.Producto" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Carrito - Holly's Baking</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        body { background-color: #f8f5f2; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }
        .navbar-custom { background-color: #6F4E37; }
        .navbar-custom .navbar-brand, .navbar-custom .nav-link { color: white; }
        .cart-container {
            background: white;
            border-radius: 20px;
            padding: 40px;
            margin-top: 30px;
            box-shadow: 0 5px 20px rgba(0,0,0,0.1);
        }
        .cart-item {
            border-bottom: 1px solid #eee;
            padding: 20px 0;
        }
        .cart-item:last-child { border-bottom: none; }
        .cart-img {
            width: 100px;
            height: 100px;
            object-fit: cover;
            border-radius: 10px;
        }
        .sin-imagen {
            width: 100px;
            height: 100px;
            background: #f0f0f0;
            border-radius: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 2rem;
        }
        .cart-total {
            font-size: 2rem;
            color: #6F4E37;
            font-weight: bold;
        }
        .btn-pagar {
            background-color: #742284;
            color: white;
            border: none;
            border-radius: 30px;
            padding: 15px 40px;
            font-size: 1.2rem;
            width: 100%;
            transition: all 0.3s;
        }
        .btn-pagar:hover {
            background-color: #5a1a6b;
            color: white;
            transform: scale(1.02);
        }
        .btn-eliminar {
            color: #dc3545;
            background: none;
            border: none;
            font-size: 1.2rem;
        }
        .btn-eliminar:hover { color: #a71d2a; }
        .empty-cart {
            text-align: center;
            padding: 60px 0;
        }
        .descuento-badge {
            background-color: #28a745;
            color: white;
            padding: 5px 15px;
            border-radius: 20px;
            font-size: 0.9rem;
        }
    </style>
</head>
<body>

    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-custom">
        <div class="container">
            <a class="navbar-brand" href="CatalogoServlet">
                <i class="bi bi-shop"></i> Holly's Baking
            </a>
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="CatalogoServlet">
                            <i class="bi bi-arrow-left"></i> Seguir Comprando
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container">
        <div class="cart-container">
            <h2 class="mb-4"><i class="bi bi-cart3"></i> Tu Carrito</h2>

            <%
                List<Producto> carrito = (List<Producto>) request.getAttribute("carrito");
                Double subtotal = (Double) request.getAttribute("subtotal");
                Double total = (Double) request.getAttribute("total");
                String tipoDescuento = (String) request.getAttribute("tipoDescuento");
                
                if (subtotal == null) subtotal = 0.0;
                if (total == null) total = 0.0;
                double descuento = subtotal - total;
                
                if (carrito != null && !carrito.isEmpty()) {
            %>

            <!-- Items del carrito -->
            <% for (Producto p : carrito) { %>
            <div class="cart-item row align-items-center">
                <div class="col-md-2">
                    <% if(p.getImagen() != null) { %>
                        <img src="ImagenServlet?id=<%= p.getIdProducto() %>" class="cart-img" alt="<%= p.getNombreProducto() %>">
                    <% } else { %>
                        <div class="sin-imagen"><i class="bi bi-cake2" style="color: #6F4E37;"></i></div>
                    <% } %>
                </div>
                <div class="col-md-6">
                    <h5><%= p.getNombreProducto() %></h5>
                    <span class="badge bg-secondary"><%= p.getCategoria() != null ? p.getCategoria() : "Sin categoria" %></span>
                </div>
                <div class="col-md-3 text-end">
                    <h5 class="text-brown">S/ <%= p.getPrecioVenta() %></h5>
                </div>
                <div class="col-md-1 text-end">
                    <form action="CarritoServlet" method="POST" style="display: inline;">
                        <input type="hidden" name="accion" value="eliminar">
                        <input type="hidden" name="id" value="<%= p.getIdProducto() %>">
                        <button type="submit" class="btn-eliminar" onclick="return confirm('Eliminar este producto?')">
                            <i class="bi bi-trash"></i>
                        </button>
                    </form>
                </div>
            </div>
            <% } %>

            <!-- Total con descuento visible -->
            <div class="row mt-4">
                <div class="col-md-6">
                    <a href="CatalogoServlet" class="btn btn-outline-secondary">
                        <i class="bi bi-arrow-left"></i> Seguir Comprando
                    </a>
                </div>
                <div class="col-md-6 text-end">
                    
                    <% if (descuento > 0) { %>
                        <p class="text-muted mb-1">
                            Subtotal: <del>S/ <%= String.format("%.2f", subtotal) %></del>
                        </p>
                        <p class="mb-2">
                            <span class="descuento-badge">
                                <i class="bi bi-tag-fill"></i> <%= tipoDescuento != null ? tipoDescuento : "Descuento aplicado" %>: -S/ <%= String.format("%.2f", descuento) %>
                            </span>
                        </p>
                    <% } %>
                    
                    <div class="cart-total mb-3">S/ <%= String.format("%.2f", total) %></div>
                    
                    <a href="PagoServlet" class="btn btn-pagar">
                        <i class="bi bi-credit-card"></i> Pagar con Yape
                    </a>
                </div>
            </div>

            <% } else { %>
            <!-- Carrito vacio -->
            <div class="empty-cart">
                <i class="bi bi-cart-x" style="font-size: 5rem; color: #ccc;"></i>
                <h3 class="mt-3 text-muted">Tu carrito esta vacio</h3>
                <p class="text-muted">Agrega algunos deliciosos productos!</p>
                <a href="CatalogoServlet" class="btn btn-primary btn-lg mt-3" style="background-color: #6F4E37; border: none;">
                    <i class="bi bi-shop-window"></i> Ver Catalogo
                </a>
            </div>
            <% } %>

        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>