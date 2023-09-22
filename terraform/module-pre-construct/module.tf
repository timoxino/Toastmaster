module "pubsub" {
  source = "../pubsub-topic-subscription"
}

module "gcs" {
  source = "../gcs-bucket"
}

module "secrets" {
  source = "../secret-sa-binding"
}