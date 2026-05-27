variable "project_name" {
  type        = string
  description = "Project name for resource tagging"
}

variable "vpc_id" {
  type        = string
  description = "VPC ID where the resources will be created"
}

variable "public_subnets" {
  type        = list(string)
  description = "List of public subnet IDs for the ALB"
}

variable "container_image" {
  type        = string
  description = "The ECR image URI for the Java application"
}

variable "container_port" {
  type    = number
  default = 8080
}

variable "cpu_threshold" {
  type        = number
  default     = 70.0
  description = "CPU percentage to trigger auto scaling"
}