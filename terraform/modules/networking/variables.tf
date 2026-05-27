variable "project_name" {
  type        = string
  description = "Project name used for tagging resources"
}

variable "vpc_cidr" {
  type        = string
  default     = "10.0.0.0/16"
  description = "The IP range for the VPC"
}