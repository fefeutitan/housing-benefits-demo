# Housing Benefits Demo API

## Applicants

### `POST /api/applicants`

Creates an applicant.

Request body:

```json
{
  "name": "Maria Silva",
  "email": "maria@example.com"
}
```

Returns `201 Created`. Invalid name or email returns `400 Bad Request`; a duplicate email returns `409 Conflict`.

### `GET /api/applicants`

Returns all applicants with status `200 OK`.

### `GET /api/applicants/{id}`

Returns one applicant with status `200 OK`. A missing UUID returns `404 Not Found`.
