variable "project_name" {
  type        = string
  description = "Project name for resource tagging"
}

variable "vpc_id" {
  type        = string
  description = "VPC ID where the database will reside"
}

variable "subnet_ids" {
  type        = list(string)
  description = "Subnets where the RDS instance will be deployed (Multi-AZ)"
}

variable "db_password" {
  type        = string
  sensitive   = true
  description = "Master password for PostgreSQL"
}

variable "allowed_sg_id" {
  type        = string
  description = "Security Group ID allowed to connect to the database (ECS SG)"
}