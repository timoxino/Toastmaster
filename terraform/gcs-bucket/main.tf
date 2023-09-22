resource "google_storage_bucket" "gcs_bucket_cv" {
  name          = "interview_cv"
  location      = "US"
  project       = "timoxino"
}

resource "google_storage_bucket_iam_binding" "bucket_binding_cv" {
  bucket = google_storage_bucket.gcs_bucket_cv.name
  role   = "roles/storage.objectAdmin"

  members = [
    "serviceAccount:908519591128-compute@developer.gserviceaccount.com"
  ]
}