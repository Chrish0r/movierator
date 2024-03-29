<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Join us!</title>
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
</head>
<body>
	<section class="vh-100 bg-image"
		style="background-image: url('https://mdbcdn.b-cdn.net/img/Photos/new-templates/search-box/img4.webp');">
		<div class="mask d-flex align-items-center h-100 gradient-custom-3">
			<div class="container h-100">
				<div
					class="row d-flex justify-content-center align-items-center h-100">
					<div class="col-12 col-md-9 col-lg-7 col-xl-6">
						<div class="card" style="border-radius: 15px;">
							<div class="card-body p-5">
								<h2 class="text-uppercase text-center mb-5">Create an
									account</h2>
								<form:form method="POST" modelAttribute="registerForm" class="form-horizontal" action="register/confirmation">  
									
									<fieldset>
										<div class="control-group">
											<!-- User Name-->
											<label class="control-label" for="username">User Name</label>
											<div class="controls">
												<input class="input-xlarge" id="username" type="text"
													name="username" placeholder=" e.g., spaceman"
													required="required" />
											</div>
										</div>
										<div class="control-group">
											<!-- E-mail-->
											<label class="control-label" for="email">E-mail</label>
											<div class="controls">
												<input class="input-xlarge" id="email" type="text"
													name="email" placeholder="Enter your email"
													required="required" />
											</div>
										</div>
										<div class="control-group">
											<!-- Password-->
											<label class="control-label" for="password">Password</label>
											<div class="controls">
												<input class="input-xlarge" id="password" type="password"
													name="password" placeholder="At least 4 characters"
													required="required" />
											</div>
										</div>
										<div class="control-group">
											<!-- Password-->
											<label class="control-label" for="password_confirm">Confirm Password
												</label>
											<div class="controls">
												<input class="input-xlarge" id="password_confirm"
													type="password" name="repeated_password"
													placeholder="Confirm password..." required="required" />
											</div>
										</div>
										<div class="control-group">
											<!-- Button-->
											<div class="controls">
												<button class="btn btn-success">Register</button>
												<node></node>
												<p class="text-center">
													Have an account? <a class="navbar-brand" href="login">Log
														In</a>
												</p>
											</div>
										</div>
									</fieldset>
								</form:form >
								<footer class="footer text-center" style="margin-top: 5%">&copy;
									All rights reserved </footer>

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>



</body>
</html>