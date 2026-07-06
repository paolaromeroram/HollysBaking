-<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Dashboard - Holly's Baking</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- FontAwesome para los íconos -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body { background-color: #f4f6f9; }
        .sidebar { min-height: 100vh; background-color: #343a40; color: white; padding-top: 20px; }
        .sidebar a { color: white; text-decoration: none; display: block; padding: 15px; border-bottom: 1px solid #495057; }
        .sidebar a:hover, .sidebar a.active-link { background-color: #6F4E37; } /* Marrón principal de la marca */
        
        .card-custom { border-radius: 10px; border: none; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        .small-box { border-radius: 10px; position: relative; display: block; margin-bottom: 20px; box-shadow: 0 1px 1px rgba(0,0,0,0.1); overflow: hidden; }
        .small-box > .inner { padding: 20px; color: white; }
        .small-box h3 { font-size: 38px; font-weight: bold; margin: 0 0 10px 0; white-space: nowrap; padding: 0; }
        .small-box p { font-size: 15px; margin-bottom: 0; }
        .small-box .icon { position: absolute; top: 15px; right: 20px; font-size: 60px; color: rgba(0,0,0,0.15); }
        .small-box-footer { position: relative; text-align: center; padding: 3px 0; color: rgba(255,255,255,0.8); display: block; z-index: 10; background: rgba(0,0,0,0.1); text-decoration: none; }
        .small-box-footer:hover { color: #fff; background: rgba(0,0,0,0.2); }

        <!-- Paleta de colores Holly's Baking -->
        .bg-choco-dark { background-color: #4A3018 !important; }
        .bg-coffee { background-color: #6F4E37 !important; }
        .bg-caramel { background-color: #8B5A2B !important; }
        .bg-latte { background-color: #A67B5B !important; }
    </style>
</head>
<body>

<div class="container-fluid">
    <div class="row">
        <!-- MENÚ LATERAL -->
        <nav class="col-md-2 sidebar">
            <h5 class="text-center font-weight-bold">Holly's Baking</h5>
            <hr>
            <!-- Enlace activo con el color marrón de la marca -->
           <a href="DashboardServlet" class="active-link"><i class="fas fa-home"></i> Dashboard Principal</a>
            <a href="InsumoServlet"><i class="fas fa-box"></i> Gestionar Insumos</a>
            <a href="ProductoServlet"><i class="fas fa-cake-candles"></i> Gestionar Productos</a>
            <a href="RecetaServlet"><i class="fas fa-book"></i> Ver Recetas</a>
            <hr>
            <a href="login.jsp" class="text-danger"><i class="fas fa-sign-out-alt"></i> Cerrar Sesión</a>
        </nav>

        <!-- CONTENIDO PRINCIPAL -->
        <main class="col-md-10 p-4">
            <h2 class="mb-4" style="color: #4A3018;">Dashboard Principal</h2>
            
            <!-- TARJETAS DE RESUMEN (Paleta Marrón) -->
            <div class="row">
                <!-- Tarjeta 1: Pedidos -->
<div class="col-lg-3 col-6">
    <div class="small-box bg-choco-dark">
        <div class="inner">
            <h3>${pedidosDelMes}</h3>
            <p>Pedidos del Mes</p>
        </div>
        <div class="icon"><i class="fas fa-shopping-bag"></i></div>
        <a href="#" class="small-box-footer">Ver más <i class="fas fa-arrow-circle-right"></i></a>
    </div>
</div>

<!-- Tarjeta 2: Productos en Stock (ya estaba bien, solo asegúrate) -->
<div class="col-lg-3 col-6">
    <div class="small-box bg-coffee">
        <div class="inner">
            <h3>${totalProductos != null ? totalProductos : 0}</h3>
            <p>Productos en Stock</p>
        </div>
        <div class="icon"><i class="fas fa-chart-bar"></i></div>
        <a href="ProductoServlet" class="small-box-footer">Ver más <i class="fas fa-arrow-circle-right"></i></a>
    </div>
</div>

<!-- Tarjeta 3: Ventas -->
<div class="col-lg-3 col-6">
    <div class="small-box bg-caramel">
        <div class="inner">
            <h3>S/ ${ventasDelMes}</h3>
            <p>Ventas del Mes</p>
        </div>
        <div class="icon"><i class="fas fa-money-bill-wave"></i></div>
        <a href="#" class="small-box-footer">Ver más <i class="fas fa-arrow-circle-right"></i></a>
    </div>
</div>

<!-- Tarjeta 4: Bajo Stock -->
<div class="col-lg-3 col-6">
    <div class="small-box bg-latte">
        <div class="inner">
            <h3>${bajoStock}</h3>
            <p>Productos Bajo Stock</p>
        </div>
        <div class="icon"><i class="fas fa-exclamation-triangle"></i></div>
        <a href="#" class="small-box-footer">Ver más <i class="fas fa-arrow-circle-right"></i></a>
    </div>
</div>

            <!-- GRÁFICO Y TABLA -->
            <div class="row mt-4">
                <!-- Columna del Gráfico -->
                <div class="col-md-8">
                    <div class="card card-custom h-100">
                        <div class="card-header bg-white" style="color: #6F4E37;">
                            <h5 class="card-title m-0 fw-bold"><i class="fas fa-chart-line"></i> Ventas Mensuales (S/)</h5>
                        </div>
                        <div class="card-body">
                            <canvas id="ventasChart" height="100"></canvas>
                        </div>
                    </div>
                </div>

                <!-- Columna de la Tabla -->
                <div class="col-md-4">
                    <div class="card card-custom h-100">
                        <div class="card-header bg-white" style="color: #6F4E37;">
                            <h5 class="card-title m-0 fw-bold"><i class="fas fa-star"></i> Top 5 Productos</h5>
                        </div>
                        <div class="card-body p-0">
                            <table class="table table-striped table-hover m-0">
                                <thead style="background-color: #343a40; color: white;">
                                    <tr>
                                        <th>Producto</th>
                                        <th>Precio</th>
                                        <th>Vendidos</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td colspan="3" class="text-center text-muted py-4">Aún no hay ventas registradas</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

        </main>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<!-- Lógica del Gráfico en Tonos Marrones -->
<script>
    const ctx = document.getElementById('ventasChart').getContext('2d');
    const ventasChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul'], 
            datasets: [{
                label: 'Ventas Mensuales (S/)',
                data: [0, 0, 0, 0, 0, 0, 0], 
                backgroundColor: 'rgba(111, 78, 55, 0.7)', /* Color #6F4E37 con transparencia */
                borderColor: 'rgba(111, 78, 55, 1)',
                borderWidth: 2,
                borderRadius: 4 /* Bordes redondeados en las barras */
            }]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
</script>

</body>
</html>