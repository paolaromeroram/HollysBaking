<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<%@page import="Modelo.entidades.Receta" %>
<%@page import="Modelo.entidades.Producto" %>
<%@page import="Modelo.entidades.Insumo" %>

<%-- Si no hay lista, redirigir al servlet --%>
<%
    if (request.getAttribute("listaRecetas") == null) {
        response.sendRedirect("RecetaServlet");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Recetas - Holly's Baking</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .sidebar { 
            min-height: 100vh; 
            background-color: #343a40; 
            color: white; 
            padding-top: 20px; 
        }
        .sidebar a { 
            color: white; 
            text-decoration: none; 
            display: block; 
            padding: 15px; 
            border-bottom: 1px solid #495057; 
        }
        .sidebar a:hover { 
            background-color: #6F4E37; 
        }
        .sidebar a.active {
            background-color: #6F4E37;
            font-weight: bold;
        }
    </style>
</head>
<body class="bg-light">

<div class="container-fluid">
    <div class="row">
        <!-- MENÚ LATERAL -->
        <nav class="col-md-2 sidebar">
            <h5 class="text-center">Holly's Baking</h5>
            <hr>
            <a href="DashboardServlet">🏠 Dashboard Principal</a>
            <a href="InsumoServlet">📦 Gestionar Insumos</a>
            <a href="ProductoServlet">🍰 Gestionar Productos</a>
            <a href="RecetaServlet" class="active">📖 Ver Recetas</a>
            <hr>
            <a href="login.jsp" class="text-danger">🚪 Cerrar Sesión</a>
        </nav>

        <!-- CONTENIDO PRINCIPAL -->
        <main class="col-md-10 p-4">
            
            <h2 class="mb-4">📖 Gestión de Recetas</h2>
            <p class="text-muted">Asigna insumos a cada producto para crear recetas</p>

            <%-- MENSAJES DE ERROR O ÉXITO --%>
            <%
                String error = (String) request.getAttribute("error");
                String mensaje = (String) request.getAttribute("mensaje");
                if (error != null) {
            %>
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <strong>Error:</strong> <%= error %>
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            <% } else if (mensaje != null) { %>
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <strong>¡Éxito!</strong> <%= mensaje %>
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            <% } %>

            <!-- Formulario para crear receta -->
            <div class="card p-4 mb-4 shadow-sm">
                <h5 class="mb-3">➕ Crear Nueva Receta</h5>
                <form action="RecetaServlet" method="POST" class="row g-3">
                    <div class="col-md-4">
                        <label class="form-label">Producto</label>
                        <select name="idProducto" class="form-select" required>
                            <option value="">Seleccione un producto...</option>
                            <% 
                                List<Producto> productos = (List<Producto>) request.getAttribute("listaProductos");
                                if(productos != null && !productos.isEmpty()) {
                                    for(Producto p : productos) {
                            %>
                            <option value="<%= p.getIdProducto() %>"><%= p.getNombreProducto() %></option>
                            <% 
                                    }
                                } else {
                            %>
                            <option value="" disabled>No hay productos disponibles</option>
                            <% } %>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">Insumo</label>
                        <select name="idInsumo" class="form-select" required>
                            <option value="">Seleccione un insumo...</option>
                            <% 
                                List<Insumo> insumos = (List<Insumo>) request.getAttribute("listaInsumos");
                                if(insumos != null && !insumos.isEmpty()) {
                                    for(Insumo i : insumos) {
                            %>
                            <option value="<%= i.getIdInsumo() %>"><%= i.getNombreInsumo() %> (<%= i.getUnidadMedida() %>)</option>
                            <% 
                                    }
                                } else {
                            %>
                            <option value="" disabled>No hay insumos disponibles</option>
                            <% } %>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">Cantidad Requerida</label>
                        <input type="number" step="0.01" name="cantidad" class="form-control" placeholder="Ej: 2.5" required>
                    </div>
                    <div class="col-md-2 d-flex align-items-end">
                        <button type="submit" class="btn btn-success w-100">Guardar Receta</button>
                    </div>
                </form>
            </div>

            <!-- Tabla de Recetas -->
            <h5 class="mb-3">📋 Recetas Registradas</h5>
            <table class="table table-striped table-hover shadow-sm bg-white">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Producto</th>
                        <th>Insumo</th>
                        <th>Cantidad Requerida</th>
                        <th>Unidad</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        List<Receta> lista = (List<Receta>) request.getAttribute("listaRecetas");
                        if(lista != null && !lista.isEmpty()) {
                            for(Receta r : lista) {
                    %>
                    <tr>
                        <td><%= r.getIdReceta() %></td>
                        <td><strong><%= r.getNombreProducto() %></strong></td>
                        <td><%= r.getNombreInsumo() %></td>
                        <td><%= r.getCantidadRequerida() %></td>
                        <td><%= r.getUnidadInsumo() %></td>
                    </tr>
                    <% 
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="5" class="text-center text-muted">No hay recetas registradas</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </main>
    </div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>