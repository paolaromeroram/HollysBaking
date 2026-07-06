<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<%@page import="Modelo.entidades.Producto" %>

<%-- Si no hay lista, redirigir al servlet --%>
<%
    if (request.getAttribute("listaProductos") == null) {
        response.sendRedirect("CatalogoServlet");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Catálogo - Holly's Baking</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        body {
            background-color: #f8f5f2;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        .navbar-custom {
            background-color: #6F4E37;
        }
        .navbar-custom .navbar-brand, .navbar-custom .nav-link {
            color: white;
        }
        .hero-section {
            background-color: #6F4E37;
            color: white;
            padding: 40px 0;
            text-align: center;
            margin-bottom: 30px;
        }
        .filter-section {
            background: white;
            padding: 15px;
            border-radius: 10px;
            margin-bottom: 30px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .product-card {
            border: none;
            border-radius: 15px;
            overflow: hidden;
            transition: transform 0.3s, box-shadow 0.3s;
            background: white;
            height: 100%;
            cursor: pointer;
        }
        .product-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 30px rgba(0,0,0,0.15);
        }
        .product-img {
            height: 200px;
            background-color: #e9ecef;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 4rem;
            color: #6F4E37;
        }
        .product-body {
            padding: 20px;
        }
        .product-title {
            font-size: 1.25rem;
            font-weight: bold;
            color: #333;
            margin-bottom: 5px;
        }
        .product-category {
            color: #6c757d;
            font-size: 0.9rem;
            margin-bottom: 10px;
        }
        .product-price {
            font-size: 1.5rem;
            color: #6F4E37;
            font-weight: bold;
            margin-bottom: 15px;
        }
        .btn-order {
            background-color: #6F4E37;
            color: white;
            border: none;
            border-radius: 25px;
            padding: 10px 30px;
            width: 100%;
            transition: background-color 0.3s;
        }
        .btn-order:hover {
            background-color: #5a3f2d;
            color: white;
        }
        .btn-filter {
            border-radius: 20px;
            margin: 0 5px;
        }
        .btn-filter.active {
            background-color: #6F4E37;
            color: white;
            border-color: #6F4E37;
        }
        .cart-badge {
            position: relative;
            top: -5px;
            font-size: 0.7rem;
        }
        .footer-custom {
            background-color: #343a40;
            color: white;
            padding: 30px 0;
            margin-top: 60px;
            text-align: center;
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
                        <a class="nav-link" href="CarritoServlet">
                            <i class="bi bi-cart3"></i> Carrito
                            <%
                                List<Producto> carrito = (List<Producto>) session.getAttribute("carrito");
                                int cantidadCarrito = (carrito != null) ? carrito.size() : 0;
                                if (cantidadCarrito > 0) {
                            %>
                            <span class="badge bg-danger cart-badge"><%= cantidadCarrito %></span>
                            <% } %>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="login.jsp">
                            <i class="bi bi-person-lock"></i> Acceso Trabajadores
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Hero Section -->
    <div class="hero-section">
        <div class="container">
            <h1><i class="bi bi-cup-hot"></i> Nuestros Productos</h1>
            <p>Deliciosos postres hechos con amor y los mejores ingredientes</p>
        </div>
    </div>

    <div class="container">
        <!-- Filtros por Categoría -->
        <div class="filter-section text-center">
            <h5 class="mb-3"><i class="bi bi-funnel"></i> Filtrar por Categoría</h5>
            <div class="d-flex justify-content-center flex-wrap">
                <a href="CatalogoServlet" class="btn btn-outline-secondary btn-filter <%= request.getAttribute("categoriaSeleccionada") == null ? "active" : "" %>">
                    Todas
                </a>
                <% 
                    List<String> categorias = (List<String>) request.getAttribute("categorias");
                    String catSeleccionada = (String) request.getAttribute("categoriaSeleccionada");
                    if (categorias != null) {
                        for (String cat : categorias) {
                %>
                <a href="CatalogoServlet?categoria=<%= cat %>" 
                   class="btn btn-outline-secondary btn-filter <%= cat.equals(catSeleccionada) ? "active" : "" %>">
                    <%= cat %>
                </a>
                <% 
                        }
                    }
                %>
            </div>
        </div>

        <!-- Productos -->
        <div class="row g-4">
            <% 
                List<Producto> lista = (List<Producto>) request.getAttribute("listaProductos");
                if (lista != null && !lista.isEmpty()) {
                    for (Producto p : lista) {
            %>
           <div class="col-md-4 col-lg-3">
    <div class="product-card" onclick="window.location.href='DetalleProductoServlet?id=<%= p.getIdProducto() %>'">
        
        <%-- IMAGEN REAL O ICONO POR DEFECTO --%>
        <% if(p.getImagen() != null) { %>
            <img src="ImagenServlet?id=<%= p.getIdProducto() %>" 
                 alt="<%= p.getNombreProducto() %>"
                 style="width: 100%; height: 200px; object-fit: cover; border-radius: 15px 15px 0 0;">
        <% } else { %>
            <div class="product-img">
                <i class="bi bi-cake2"></i>
            </div>
        <% } %>
        
        <div class="product-body">
            <span class="badge bg-secondary mb-2"><%= p.getCategoria() != null ? p.getCategoria() : "Sin categoría" %></span>
            <h5 class="product-title"><%= p.getNombreProducto() %></h5>
            <div class="product-price">S/ <%= p.getPrecioVenta() %></div>
        </div>
    </div>
    
    <form action="CarritoServlet" method="POST" class="mt-2">
        <input type="hidden" name="accion" value="agregar">
        <input type="hidden" name="id" value="<%= p.getIdProducto() %>">
        <button type="submit" class="btn btn-order">
            <i class="bi bi-cart-plus"></i> Agregar al Carrito
        </button>
    </form>
</div>
            <% 
                    }
                } else {
            %>
            <div class="col-12 text-center py-5">
                <i class="bi bi-inbox" style="font-size: 4rem; color: #ccc;"></i>
                <h3 class="mt-3 text-muted">No hay productos en esta categoría</h3>
                <a href="CatalogoServlet" class="btn btn-outline-primary mt-3">Ver todos los productos</a>
            </div>
            <% } %>
        </div>
    </div>

    <!-- Footer -->
    <footer class="footer-custom">
        <div class="container">
            <h5><i class="bi bi-shop"></i> Holly's Baking</h5>
            <p class="mb-0">© 2026 Todos los derechos reservados</p>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>