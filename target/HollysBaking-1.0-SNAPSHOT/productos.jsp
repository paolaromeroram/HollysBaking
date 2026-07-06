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
            