# --- Project Metadata ---
variable "project_name" {
  description = "Name of the project used for resource naming"
  type        = string
  default     = "url-shortener"
}

variable "environment" {
  description = "Deployment environment (dev/prod)"
  type        = string
  default     = "prod"
}

# --- AWS Configuration ---
variable "aws_region" {
  description = "AWS region for deployment"
  type        = string
  default     = "us-east-1"
}

# --- Compute Configuration ---
variable "container_image" {
  description = "The URI of the Docker image in ECR"
  type        = string
}

variable "container_port" {
  description = "Port the application is listening on"
  type        = number
  default     = 8080
}

# --- Database Configuration ---
variable "db_password" {
  description = "Master password for the RDS instance"
  type        = string
  sensitive   = true
}