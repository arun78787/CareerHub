// Converts local datetime-local input to UTC ISO string
export function localToUtcIso(local: string) {
const date = new Date(local);
return new Date(date.getTime() - date.getTimezoneOffset() * 60000).toISOString();
}