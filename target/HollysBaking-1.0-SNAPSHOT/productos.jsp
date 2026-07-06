<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<%@page import="Modelo.entidades.Producto" %>

<%-- Si no hay lista, redirigir al servlet --%>
<%
    if (request.getAttribute("listaProductos") == null) {
        response.sendRedirect("ProductoServlet");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Productos - Holly's Baking</title>
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
        .sidebar a:hover, .sidebar a.active { 
            background-color: #6F4E37; 
        }
        .bg-pink {
            background-color: #e83e8c !important;
            color: white;
        }
        .bg-brown {
            background-color: #8B4513 !important;
            color: white;
        }
        .producto-img {
            width: 80px;
            height: 80px;
            object-fit: cover;
            border-radius: 8px;
        }
        .sin-imagen {
            width: 80px;
            height: 80px;
            background: #f0f0f0;
            border-radius: 8px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 24px;
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
            <a href="ProductoServlet" class="active">🍰 Gestionar Productos</a>
            <a href="RecetaServlet">📖 Ver Recetas</a>
            <hr>
            <a href="login.jsp" class="text-danger">🚪 Cerrar Sesión</a>
        </nav>

        <!-- CONTENIDO PRINCIPAL -->
        <main class="col-md-10 p-4">
            
            <h2 class="mb-4">Gestión de Productos</h2>

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

            <!-- Formulario con Imagen -->
            <div class="card p-4 mb-4 shadow-sm">
                <h5 class="mb-3">Registrar Nuevo Producto</h5>
                <form action="ProductoServlet" method="POST" enctype="multipart/form-data" class="row g-3">
                    <div class="col-md-3">
                        <label class="form-label">Nombre</label>
                        <input type="text" name="nombre" class="form-control" placeholder="Nombre del Producto" required>
                    </div>
                    <div class="col-md-2">
                        <label class="form-label">Precio</label>
                        <input type="number" step="0.01" name="precio" class="form-control" placeholder="Precio Venta" required>
                    </div>
                    <div class="col-md-2">
                        <label class="form-label">Categoría</label>
                        <select name="categoria" class="form-select" required>
                            <option value="">Seleccione...</option>
                            <option value="Dulce">🍰 Dulce</option>
                            <option value="Salada">🥖 Salada</option>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">Descripción</label>
                        <input type="text" name="descripcion" class="form-control" placeholder="Descripción (opcional)">
                    </div>
                    <div class="col-md-2">
                        <label class="form-label">Imagen</label>
                        <input type="file" name="imagen" class="form-control" accept="image/*">
                    </div>
                    <div class="col-md-12 mt-3">
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-save"></i> Guardar Producto
                        </button>
                    </div>
                </form>
            </div>

            <!-- Tabla con Imagen -->
            <table class="table table-striped table-hover shadow-sm bg-white">
               <thead class="table-dark">
    <tr>
        <th>ID</th>
        <th>Imagen</th>
        <th>Nombre</th>
        <th>Descripción</th>
        <th>Precio</th>
        <th>Categoría</th>
        <th>Acciones</th>  <!-- NUEVA COLUMNA -->
    </tr>
</thead>
<tbody>
    <% 
        List<Producto> lista = (List<Producto>) request.getAttribute("listaProductos");
        if(lista != null && !lista.isEmpty()) {
            for(Producto p : lista) {
    %>
    <tr>
        <td><%= p.getIdProducto() %></td>
        <td>
            <% if(p.getImagen() != null) { %>
                <img src="ImagenServlet?id=<%= p.getIdProducto() %>" 
                     alt="<%= p.getNombreProducto() %>" 
                     style="width: 80px; height: 80px; object-fit: cover; border-radius: 8px;">
            <% } else { %>
                <div style="width: 80px; height: 80px; background: #f0f0f0; border-radius: 8px; display: flex; align-items: center; justify-content: center; font-size: 24px;">🍰</div>
            <% } %>
        </td>
        <td><%= p.getNombreProducto() %></td>
        <td><%= p.getDescripcion() != null ? p.getDescripcion() : "-" %></td>
        <td>S/ <%= p.getPrecioVenta() %></td>
        <td>
            <% if("Dulce".equals(p.getCategoria())) { %>
                <span class="badge bg-pink">🍰 Dulce</span>
            <% } else if("Salada".equals(p.getCategoria())) { %>
                <span class="badge bg-brown">🥖 Salada</span>
            <% } else { %>
                <span class="badge bg-secondary">Sin categoría</span>
            <% } %>
        </td>
        <td>  <!-- NUEVA COLUMNA: BOTÓN ELIMINAR -->
            <a href="ProductoServlet?accion=eliminar&id=<%= p.getIdProducto() %>" 
               class="btn btn-danger btn-sm"
               onclick="return confirm('¿Estás seguro de eliminar este producto?')">
                🗑️ Eliminar
            </a>
        </td>
    </tr>
    <% 
            }
        } else {
    %>
    <tr>
        <td colspan="7" class="text-center text-muted">No hay productos registrados</td>
    </tr>
    <% } %>
</tbody> 
            </table>
        </main>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>