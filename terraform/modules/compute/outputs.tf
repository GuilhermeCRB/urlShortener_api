output "alb_dns_name" {
  value       = aws_lb.main.dns_name
  description = "The public DNS of the Load Balancer"
}

output "ecs_security_group_id" {
  value       = aws_security_group.ecs_sg.id
  description = "The SG ID of the application, to be used by the RDS module"
}