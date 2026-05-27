output "frontend_url" {
  value       = "http://${module.frontend.website_url}"
  description = "URL to access the Angular application"
}

output "backend_alb_dns" {
  value       = module.compute.alb_dns_name
  description = "Public DNS of the Load Balancer (API Gateway)"
}

output "rds_endpoint" {
  value       = module.database.db_endpoint
  description = "The connection endpoint for the database"
}