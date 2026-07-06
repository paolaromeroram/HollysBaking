<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<%@page import="modelo.entidades.Producto" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Carrito - Holly's Baking</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        body { background-color: #f8f5f2; }
        .navbar-custom { background-color: #6F4E37; }
        .navbar-custom .navbar-brand, .navbar-custom .nav-link { color: white; }
        .cart-section {
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
        .item-img {
            width: 80px;
            height: 80px;
            background-color: #e9ecef;
            border-radius: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 2rem;
            color: #6F4E37;
        }
        .total-section {
            background-color: #6F4E37;
            color: white;
            border-radius: 15px;
            padding: 20px;
            margin-top: 20px;
        }
        .btn-checkout {
            background-color: white;
            color: #6F4E37;
            border: none;
            border-radius: 25px;
            padding: 12px 30px;
            font-weight: bold;
        }
        .btn-checkout:hover {
            background-color: #f8f5f2;
        }
        .empty-cart {
            text-align: center;
            padding: 60px 20px;
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
                            <i class="bi bi-shop-window"></i> Seguir Comprando
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container">
        <div class="cart-section">
            <h2 class="mb-4"><i class="bi bi-cart3"></i> Tu Carrito</h2>
            
            <% 
                List<Producto> carrito = (List<Producto>) request.getAttribute("carrito");
                Double total = (Double) request.getAttribute("total");
                
                if (carrito != null && !carrito.isEmpty()) {
            %>
            
            <!-- Items del carrito -->
            <% for (Producto p : carrito) { %>
            <div class="cart-item d-flex align-items-center">
                <div class="item-img me-3">
                    <i class="bi bi-cake2"></i>
                </div>
                <div class="flex-grow-1">
                    <h5 class="mb-1"><%= p.getNombreProducto() %></h5>
                    <span class="badge bg-secondary"><%= p.getCategoria() != null ? p.getCategoria() : "Sin categoría" %></span>
                </div>
                <div class="text-end">
                    <div class="h5 mb-2 text-brown">S/ <%= p.getPrecioVenta() %></div>
                    <form action="CarritoServlet" method="POST" style="display: inline;">
                        <input type="hidden" name="accion" value="eliminar">
                        <input type="hidden" name="id" value="<%= p.getIdProducto() %>">
                        <button type="submit" class="btn btn-sm btn-outline-danger">
                            <i class="bi bi-trash"></i> Eliminar
                        </button>
                    </form>
                </div>
            </div>
            <% } %>
            
            <!-- Total -->
            <div class="total-section d-flex justify-content-between align-items-center">
                <div>
                    <h4 class="mb-0">Total:</h4>
                    <h2 class="mb-0">S/ <%= total %></h2>
                </div>
                <div class="text-end">
                    <form action="CarritoServlet" method="POST" class="d-inline me-2">
                        <input type="hidden" name="accion" value="vaciar">
                        <button type="submit" class="btn btn-outline-light">
                            <i class="bi bi-trash"></i> Vaciar
                        </button>
                    </form>
                    <button class="btn btn-checkout">
                        <i class="bi bi-credit-card"></i> Finalizar Compra
                    </button>
                </div>
            </div>
            
            <% } else { %>
            
            <!-- Carrito vacío -->
            <div class="empty-cart">
                <i class="bi bi-cart-x" style="font-size: 5rem; color: #ccc;"></i>
                <h3 class="mt-3 text-muted">Tu carrito está vacío</h3>
                <p class="text-muted">¡Agrega algunos deliciosos productos!</p>
                <a href="CatalogoServlet" class="btn btn-primary btn-lg mt-3" style="background-color: #6F4E37; border: none;">
                    <i class="bi bi-shop-window"></i> Ver Catálogo
                </a>
            </div>
            
            <% } %>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>