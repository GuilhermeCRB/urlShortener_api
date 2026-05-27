# 1. Networking Module: Creates VPC, Subnets, and Internet Gateway
module "network" {
  source       = "./modules/networking"
  project_name = var.project_name
}

# 2. Compute Module: Creates ECS Cluster, Fargate Service, ALB, and Auto Scaling
module "compute" {
  source          = "./modules/compute"
  project_name    = var.project_name
  vpc_id          = module.network.vpc_id
  public_subnets  = module.network.public_subnets
  container_image = var.container_image
  container_port  = var.container_port
}

# 3. Database Module: Creates RDS PostgreSQL with restricted access
module "database" {
  source        = "./modules/database"
  project_name  = var.project_name
  vpc_id        = module.network.vpc_id
  subnet_ids    = module.network.public_subnets
  db_password   = var.db_password
  allowed_sg_id = module.compute.ecs_security_group_id # Security: Only ECS can access DB
}

# 4. Storage Module: Creates S3 Bucket for Angular static hosting
module "frontend" {
  source       = "./modules/storage"
  project_name = var.project_name
  environment  = var.environment
}