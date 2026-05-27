output "vpc_id" {
  value       = aws_vpc.main.id
  description = "The ID of the created VPC"
}

output "public_subnets" {
  value       = aws_subnet.public[*].id
  description = "List of IDs for the public subnets"
}